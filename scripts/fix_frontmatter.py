#!/usr/bin/env python3
"""
Fix YAML front matter issues in Markdown files.
"""

import re
from pathlib import Path


def fix_frontmatter(file_path: Path):
    """Fix YAML frontmatter in a Markdown file."""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Find frontmatter block
        frontmatter_match = re.match(r'^---\n(.*?)\n---\n', content, re.DOTALL)
        if not frontmatter_match:
            return False

        frontmatter = frontmatter_match.group(1)
        rest_of_content = content[frontmatter_match.end():]

        # Fix title line - add quotes if it contains special characters
        def fix_title_line(match):
            key = match.group(1)
            value = match.group(2).strip()

            # If value contains special characters and isn't already quoted, quote it
            if any(char in value for char in [':', '#', '@', '&', '*', '!', '|', '>', '<', '%', '™', '®', '©']) and not (value.startswith('"') or value.startswith("'")):
                # Escape existing quotes in value
                value = value.replace('"', '\\"')
                return f'{key}: "{value}"'
            return match.group(0)

        # Fix all key-value pairs
        fixed_frontmatter = re.sub(r'^(\w+):\s*(.+)$', fix_title_line, frontmatter, flags=re.MULTILINE)

        # Rebuild content
        new_content = f"---\n{fixed_frontmatter}\n---\n{rest_of_content}"

        # Write back
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(new_content)

        return True

    except Exception as e:
        print(f"Error fixing {file_path}: {e}")
        return False


def main():
    """Fix all Markdown files in docs directory."""
    docs_dir = Path("/home/user/jmeter/docs/docs")

    # Find all markdown files
    md_files = list(docs_dir.rglob("*.md"))

    print(f"Fixing front matter in {len(md_files)} files...")

    fixed = 0
    for md_file in md_files:
        if fix_frontmatter(md_file):
            fixed += 1

    print(f"✓ Fixed {fixed} files")


if __name__ == "__main__":
    main()
