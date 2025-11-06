#!/usr/bin/env python3
"""
Split component_reference.xml into individual component files.
"""

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from convert_xml_to_markdown import XMLToMarkdownConverter


def slugify(text: str) -> str:
    """Convert text to URL-friendly slug."""
    slug = re.sub(r'[^\w\s-]', '', text.lower())
    slug = re.sub(r'[-\s]+', '-', slug)
    return slug.strip('-')


def split_component_reference(xml_path: Path, output_dir: Path):
    """Split component_reference.xml into individual component markdown files."""
    tree = ET.parse(xml_path)
    root = tree.getroot()
    converter = XMLToMarkdownConverter()

    output_dir.mkdir(parents=True, exist_ok=True)

    # Find all sections (categories)
    body = root.find("body")
    if body is None:
        return

    sections = {}
    current_section = None
    current_section_name = None

    for element in body:
        if element.tag == "section":
            section_name = element.get("name", "")
            current_section = element
            current_section_name = section_name
            sections[section_name] = []

            # Check for introduction/description
            desc = element.find("description")
            if desc is not None:
                intro_content = ""
                for child in desc:
                    intro_content += converter.convert_element(child)

                if intro_content.strip():
                    # Save section introduction
                    section_slug = slugify(section_name.split()[1] if len(section_name.split()) > 1 else section_name)
                    intro_file = output_dir / f"{section_slug}-intro.md"
                    with open(intro_file, "w", encoding="utf-8") as f:
                        f.write(f"---\n")
                        f.write(f"title: {section_name}\n")
                        f.write(f"---\n\n")
                        f.write(f"# {section_name}\n\n")
                        f.write(intro_content)

            # Process components in this section
            for component in element.findall("component"):
                component_name = component.get("name", "")
                component_index = component.get("index", "")
                screenshot = component.get("screenshot", "")

                # Build component markdown
                content = f"---\n"
                content += f"title: {component_name}\n"
                if component_index:
                    content += f"sidebar_position: {component_index.split('.')[-1]}\n"
                content += f"---\n\n"
                content += f"# {component_name}\n\n"

                # Add screenshot if available
                if screenshot:
                    # Adjust path for Docusaurus
                    screenshot_path = f"/img/{screenshot}"
                    content += f"![{component_name}]({screenshot_path})\n\n"

                # Process description
                desc = component.find("description")
                if desc is not None:
                    for child in desc:
                        content += converter.convert_element(child)

                # Process properties
                properties = component.find("properties")
                if properties is not None:
                    content += converter._convert_properties(properties)

                # Process links
                links = component.find("links")
                if links is not None:
                    content += converter.convert_element(links)

                # Save component file
                component_slug = slugify(component_name)
                component_file = output_dir / f"{component_slug}.md"
                with open(component_file, "w", encoding="utf-8") as f:
                    f.write(content)

                sections[current_section_name].append({
                    "name": component_name,
                    "slug": component_slug,
                    "index": component_index
                })

                print(f"✓ Created {component_file.name}")

    # Create index file for components
    index_content = f"---\n"
    index_content += f"title: Component Reference\n"
    index_content += f"sidebar_position: 1\n"
    index_content += f"---\n\n"
    index_content += f"# Component Reference\n\n"
    index_content += f"This reference describes the components available in Apache JMeter.\n\n"

    for section_name, components in sections.items():
        if components:
            index_content += f"## {section_name}\n\n"
            for comp in components:
                index_content += f"- [{comp['name']}](./{comp['slug']})\n"
            index_content += "\n"

    index_file = output_dir / "index.md"
    with open(index_file, "w", encoding="utf-8") as f:
        f.write(index_content)

    print(f"\n✓ Created component reference index with {len(sections)} sections")


if __name__ == "__main__":
    xml_file = Path("/home/user/jmeter/xdocs/usermanual/component_reference.xml")
    output_dir = Path("/home/user/jmeter/docs/docs/components")

    print(f"Splitting component_reference.xml into individual files...")
    split_component_reference(xml_file, output_dir)
    print(f"\n✓ Component migration complete!")
