#!/usr/bin/env python3
"""
Convert JMeter XML documentation to Markdown format for Docusaurus.
"""

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import List, Dict, Optional
import html


class XMLToMarkdownConverter:
    """Converts JMeter XML documentation to Markdown."""

    def __init__(self):
        self.current_indent = 0

    def convert_element(self, element: ET.Element, preserve_whitespace: bool = False) -> str:
        """Convert an XML element to Markdown."""
        tag = element.tag
        text = element.text or ""
        tail = element.tail or ""

        # Handle text content
        if not preserve_whitespace:
            text = text.strip()
            tail = tail.strip()

        result = ""

        # Convert based on tag type
        if tag == "p":
            result = self._process_inline_elements(element) + "\n\n"
        elif tag == "note":
            content = self._process_inline_elements(element)
            result = f":::note\n{content}\n:::\n\n"
        elif tag == "warning":
            content = self._process_inline_elements(element)
            result = f":::warning\n{content}\n:::\n\n"
        elif tag == "br":
            result = "  \n"
        elif tag == "b" or tag == "strong":
            content = self._process_inline_elements(element)
            result = f"**{content}**"
        elif tag == "i" or tag == "em":
            content = self._process_inline_elements(element)
            result = f"*{content}*"
        elif tag == "code":
            content = self._process_inline_elements(element)
            result = f"`{content}`"
        elif tag == "a":
            href = element.get("href", "")
            content = self._process_inline_elements(element)
            result = f"[{content}]({href})"
        elif tag == "ul":
            items = []
            for li in element.findall("li"):
                item_content = self._process_inline_elements(li)
                items.append(f"- {item_content}")
            result = "\n".join(items) + "\n\n"
        elif tag == "ol":
            items = []
            for i, li in enumerate(element.findall("li"), 1):
                item_content = self._process_inline_elements(li)
                items.append(f"{i}. {item_content}")
            result = "\n".join(items) + "\n\n"
        elif tag == "source":
            content = self._get_all_text(element)
            result = f"```\n{content}\n```\n\n"
        elif tag == "figure":
            image = element.find("img")
            if image is not None:
                src = image.get("src", "")
                alt = image.get("alt", "")
                result = f"![{alt}]({src})\n\n"
        elif tag == "img":
            src = element.get("src", "")
            alt = element.get("alt", "")
            result = f"![{alt}]({src})\n\n"
        elif tag == "table":
            result = self._convert_table(element)
        elif tag == "h1":
            content = self._process_inline_elements(element)
            result = f"# {content}\n\n"
        elif tag == "h2":
            content = self._process_inline_elements(element)
            result = f"## {content}\n\n"
        elif tag == "h3":
            content = self._process_inline_elements(element)
            result = f"### {content}\n\n"
        elif tag == "h4":
            content = self._process_inline_elements(element)
            result = f"#### {content}\n\n"
        elif tag == "section":
            name = element.get("name", "")
            result = f"## {name}\n\n"
            for child in element:
                if child.tag != "description":
                    result += self.convert_element(child)
        elif tag == "description":
            for child in element:
                result += self.convert_element(child)
        elif tag == "properties":
            result = self._convert_properties(element)
        elif tag == "links":
            result = "\n### See also\n\n"
            for link in element.findall("link"):
                href = link.get("href", "")
                text = link.text or href
                result += f"- [{text}]({href})\n"
            result += "\n"
        elif tag == "component":
            # Skip, handled separately
            pass
        elif tag == "complink":
            name = element.get("name", "")
            result = f"[{name}](#{self._slugify(name)})"
        elif tag == "funclink":
            name = element.get("name", "")
            result = f"[{name}](../functions#{self._slugify(name)})"
        elif tag in ["pr", "issue", "bugzilla"]:
            num = self._get_all_text(element)
            if tag == "pr":
                result = f"[PR #{num}](https://github.com/apache/jmeter/pull/{num})"
            elif tag == "issue":
                result = f"[Issue #{num}](https://github.com/apache/jmeter/issues/{num})"
            else:
                result = f"[Bug {num}](https://bz.apache.org/bugzilla/show_bug.cgi?id={num})"
        elif tag == "ch_section":
            content = self._get_all_text(element)
            result = f"\n### {content}\n\n"
        else:
            # Default: process children
            for child in element:
                result += self.convert_element(child)
            if text:
                result = text + result

        if tail:
            result += tail

        return result

    def _process_inline_elements(self, element: ET.Element) -> str:
        """Process an element that contains inline content."""
        result = element.text or ""

        for child in element:
            if child.tag == "b" or child.tag == "strong":
                text = self._process_inline_elements(child)
                result += f"**{text}**"
            elif child.tag == "i" or child.tag == "em":
                text = self._process_inline_elements(child)
                result += f"*{text}*"
            elif child.tag == "code":
                text = self._get_all_text(child)
                result += f"`{text}`"
            elif child.tag == "a":
                href = child.get("href", "")
                text = self._get_all_text(child)
                result += f"[{text}]({href})"
            elif child.tag == "br":
                result += "  \n"
            elif child.tag == "sup":
                text = self._get_all_text(child)
                result += f"<sup>{text}</sup>"
            elif child.tag == "sub":
                text = self._get_all_text(child)
                result += f"<sub>{text}</sub>"
            elif child.tag == "complink":
                name = child.get("name", "")
                result += f"[{name}](#{self._slugify(name)})"
            elif child.tag == "funclink":
                name = child.get("name", "")
                result += f"[{name}](../functions#{self._slugify(name)})"
            elif child.tag in ["pr", "issue", "bugzilla"]:
                num = self._get_all_text(child)
                if child.tag == "pr":
                    result += f"[PR #{num}](https://github.com/apache/jmeter/pull/{num})"
                elif child.tag == "issue":
                    result += f"[Issue #{num}](https://github.com/apache/jmeter/issues/{num})"
                else:
                    result += f"[Bug {num}](https://bz.apache.org/bugzilla/show_bug.cgi?id={num})"
            elif child.tag == "keycombo":
                keys = [self._get_all_text(k) for k in child.findall("keysym")]
                result += "+".join(keys)
            else:
                result += self._get_all_text(child)

            if child.tail:
                result += child.tail

        return result.strip()

    def _get_all_text(self, element: ET.Element) -> str:
        """Get all text content from an element and its children."""
        text_parts = []
        if element.text:
            text_parts.append(element.text)
        for child in element:
            text_parts.append(self._get_all_text(child))
            if child.tail:
                text_parts.append(child.tail)
        return "".join(text_parts)

    def _convert_table(self, table: ET.Element) -> str:
        """Convert an XML table to Markdown table."""
        rows = []

        # Process table rows
        for tr in table.findall(".//tr"):
            cells = []
            for td in tr.findall("td"):
                content = self._process_inline_elements(td).replace("\n", " ")
                cells.append(content)
            for th in tr.findall("th"):
                content = self._process_inline_elements(th).replace("\n", " ")
                cells.append(content)
            if cells:
                rows.append(cells)

        if not rows:
            return ""

        # Build Markdown table
        result = "| " + " | ".join(rows[0]) + " |\n"
        result += "|" + "|".join([" --- " for _ in rows[0]]) + "|\n"

        for row in rows[1:]:
            result += "| " + " | ".join(row) + " |\n"

        return result + "\n"

    def _convert_properties(self, properties: ET.Element) -> str:
        """Convert properties list to Markdown table."""
        result = "\n### Properties\n\n"
        result += "| Property | Required | Description |\n"
        result += "| --- | --- | --- |\n"

        for prop in properties.findall("property"):
            name = prop.get("name", "")
            required = prop.get("required", "No")
            desc = self._process_inline_elements(prop)
            # Escape pipe characters in description
            desc = desc.replace("|", "\\|").replace("\n", " ")
            result += f"| {name} | {required} | {desc} |\n"

        return result + "\n"

    def _slugify(self, text: str) -> str:
        """Convert text to URL-friendly slug."""
        # Remove special characters, convert to lowercase, replace spaces with hyphens
        slug = re.sub(r'[^\w\s-]', '', text.lower())
        slug = re.sub(r'[-\s]+', '-', slug)
        return slug.strip('-')

    def convert_document(self, xml_path: Path) -> Dict[str, str]:
        """Convert an XML document to Markdown.

        Returns:
            Dict with 'title', 'content' keys
        """
        tree = ET.parse(xml_path)
        root = tree.getroot()

        # Extract title
        title_elem = root.find(".//properties/title")
        title = title_elem.text if title_elem is not None else xml_path.stem

        # Convert body content
        body = root.find("body")
        content = ""

        if body is not None:
            for section in body.findall("section"):
                section_name = section.get("name", "")
                if section_name:
                    content += f"# {section_name}\n\n"

                # Process description
                desc = section.find("description")
                if desc is not None:
                    for child in desc:
                        content += self.convert_element(child)

                # Process other elements
                for child in section:
                    if child.tag != "description":
                        content += self.convert_element(child)

        return {
            "title": title,
            "content": content
        }


def main():
    """Main conversion function."""
    converter = XMLToMarkdownConverter()

    # Test with index.xml
    xml_file = Path("/home/user/jmeter/xdocs/index.xml")
    if xml_file.exists():
        result = converter.convert_document(xml_file)
        print(f"Title: {result['title']}")
        print(f"\nContent preview (first 500 chars):\n{result['content'][:500]}")
    else:
        print(f"File not found: {xml_file}")


if __name__ == "__main__":
    main()
