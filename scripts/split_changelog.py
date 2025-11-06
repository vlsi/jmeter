#!/usr/bin/env python3
"""
Split changes_history.xml into per-version files.
"""

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from convert_xml_to_markdown import XMLToMarkdownConverter


def split_changelog(xml_path: Path, output_dir: Path):
    """Split changes_history.xml into individual version markdown files."""
    converter = XMLToMarkdownConverter()
    output_dir.mkdir(parents=True, exist_ok=True)

    with open(xml_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Split by <h1> tags (version markers)
    version_pattern = r'<h1>(Version [\d.]+)\s*</h1>'
    versions = re.split(version_pattern, content)

    # Extract preamble (before first version)
    preamble = versions[0] if len(versions) > 0 else ""

    # Create index file
    index_content = "---\n"
    index_content += "title: Change History\n"
    index_content += "sidebar_position: 1\n"
    index_content += "---\n\n"
    index_content += "# History of Previous Changes\n\n"
    index_content += ":::note\n"
    index_content += "This page details the changes made in previous versions only.\n\n"
    index_content += "Current changes are detailed in [Changes](../changes).\n"
    index_content += ":::\n\n"
    index_content += "Changes sections are chronologically ordered from top (most recent) to bottom (least recent).\n\n"

    # Process version chunks (they come in pairs: version name, version content)
    version_files = []
    for i in range(1, len(versions), 2):
        if i + 1 >= len(versions):
            break

        version_name = versions[i].strip()
        version_content = versions[i + 1]

        # Extract version number for filename
        version_match = re.search(r'Version ([\d.]+)', version_name)
        if not version_match:
            continue

        version_num = version_match.group(1)
        version_slug = version_num.replace('.', '-')

        # Build markdown content
        md_content = f"---\n"
        md_content += f"title: Version {version_num}\n"
        md_content += f"---\n\n"
        md_content += f"# Version {version_num}\n\n"

        # Parse and convert the version content
        # Wrap in a temporary root element for parsing
        try:
            wrapped_content = f"<root>{version_content}</root>"
            temp_root = ET.fromstring(wrapped_content)

            for child in temp_root:
                md_content += converter.convert_element(child)

        except ET.ParseError as e:
            # If parsing fails, try a simpler approach
            print(f"Warning: XML parsing failed for version {version_num}, using text conversion")

            # Convert basic HTML to Markdown manually
            version_md = version_content

            # Convert headings
            version_md = re.sub(r'<h2>(.*?)</h2>', r'## \1\n', version_md)
            version_md = re.sub(r'<h3>(.*?)</h3>', r'### \1\n', version_md)

            # Convert lists
            version_md = re.sub(r'<ul>', '', version_md)
            version_md = re.sub(r'</ul>', '\n', version_md)
            version_md = re.sub(r'<li>', '- ', version_md)
            version_md = re.sub(r'</li>', '\n', version_md)

            # Convert code
            version_md = re.sub(r'<code>(.*?)</code>', r'`\1`', version_md)

            # Convert source blocks
            version_md = re.sub(r'<source>(.*?)</source>', r'```\n\1\n```\n', version_md, flags=re.DOTALL)

            # Convert bold/italic
            version_md = re.sub(r'<b>(.*?)</b>', r'**\1**', version_md)
            version_md = re.sub(r'<strong>(.*?)</strong>', r'**\1**', version_md)
            version_md = re.sub(r'<i>(.*?)</i>', r'*\1*', version_md)
            version_md = re.sub(r'<em>(.*?)</em>', r'*\1*', version_md)

            # Convert links
            version_md = re.sub(r'<a href="(.*?)">(.*?)</a>', r'[\2](\1)', version_md)

            # Convert paragraphs
            version_md = re.sub(r'<p>(.*?)</p>', r'\1\n\n', version_md, flags=re.DOTALL)

            # Convert PR/Issue tags
            version_md = re.sub(r'<pr>(\d+)</pr>', r'[PR #\1](https://github.com/apache/jmeter/pull/\1)', version_md)
            version_md = re.sub(r'<issue>(\d+)</issue>', r'[Issue #\1](https://github.com/apache/jmeter/issues/\1)', version_md)
            version_md = re.sub(r'<bugzilla>(\d+)</bugzilla>', r'[Bug \1](https://bz.apache.org/bugzilla/show_bug.cgi?id=\1)', version_md)

            # Convert ch_section
            version_md = re.sub(r'<ch_section>(.*?)</ch_section>', r'\n### \1\n\n', version_md)

            # Remove remaining XML tags
            version_md = re.sub(r'<[^>]+>', '', version_md)

            # Clean up multiple newlines
            version_md = re.sub(r'\n{3,}', '\n\n', version_md)

            md_content += version_md.strip() + "\n"

        # Save version file
        version_file = output_dir / f"version-{version_slug}.md"
        with open(version_file, "w", encoding="utf-8") as f:
            f.write(md_content)

        version_files.append({
            "num": version_num,
            "slug": version_slug
        })

        print(f"✓ Created version {version_num}")

    # Add version links to index
    for ver in version_files:
        index_content += f"- [Version {ver['num']}](./version-{ver['slug']})\n"

    index_file = output_dir / "index.md"
    with open(index_file, "w", encoding="utf-8") as f:
        f.write(index_content)

    print(f"\n✓ Created changelog index with {len(version_files)} versions")


if __name__ == "__main__":
    xml_file = Path("/home/user/jmeter/xdocs/changes_history.xml")
    output_dir = Path("/home/user/jmeter/docs/docs/changelog")

    print(f"Splitting changes_history.xml into individual version files...")
    split_changelog(xml_file, output_dir)
    print(f"\n✓ Changelog migration complete!")
