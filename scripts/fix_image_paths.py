#!/usr/bin/env python3
"""
Fix image paths in migrated Markdown files.
"""

import re
from pathlib import Path


def fix_image_paths(file_path: Path):
    """Fix image paths in a Markdown file."""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Fix image references to add /images/ subdirectory
        # Pattern: ![alt](/img/something.png) -> ![alt](/img/images/screenshots/something.png)
        def fix_path(match):
            alt = match.group(1)
            path = match.group(2)

            # Skip if already has /images/ in path
            if '/images/' in path:
                return match.group(0)

            # Add /images/screenshots/ to the path
            if path.startswith('/img/'):
                new_path = path.replace('/img/', '/img/images/screenshots/')
                return f'![{alt}]({new_path})'

            return match.group(0)

        new_content = re.sub(r'!\[(.*?)\]\((/img/[^)]+)\)', fix_path, content)

        if new_content != content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(new_content)
            return True

        return False

    except Exception as e:
        print(f"Error fixing {file_path}: {e}")
        return False


def main():
    """Fix all Markdown files."""
    docs_dir = Path("/home/user/jmeter/docs/docs")

    # Find all markdown files
    md_files = list(docs_dir.rglob("*.md"))

    print(f"Fixing image paths in {len(md_files)} files...")

    fixed = 0
    for md_file in md_files:
        if fix_image_paths(md_file):
            fixed += 1

    print(f"✓ Fixed {fixed} files")


if __name__ == "__main__":
    main()
