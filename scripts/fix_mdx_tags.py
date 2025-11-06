#!/usr/bin/env python3
"""
Fix MDX parsing issues by escaping XML-like tags in content.
"""

import re
from pathlib import Path


def fix_mdx_tags(file_path: Path):
    """Fix XML-like tags that MDX interprets as JSX."""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        original_content = content

        # Common XML tags that need to be escaped or converted to code
        # Pattern: <tag> that's not a standard HTML tag
        problem_tags = [
            'argument', 'property', 'component', 'function', 'variable',
            'class', 'method', 'interface', 'package', 'annotation',
            'element', 'attribute', 'jmeter', 'thread', 'sampler',
        ]

        # Escape problematic tags by wrapping in backticks if not already in code
        for tag in problem_tags:
            # Find standalone tags not in code blocks or inline code
            # Pattern: <tag> or </tag> that's not in ` ` or ``` ```
            pattern = f'(?<!`)(</?{tag}[^>]*>)(?!`)'

            def escape_tag(match):
                tag_text = match.group(1)
                # Check if we're inside a code block by looking at context
                # This is a simple heuristic
                return f'`{tag_text}`'

            content = re.sub(pattern, escape_tag, content, flags=re.IGNORECASE)

        # Fix unclosed/mismatched tags by escaping them
        # Pattern: HTML comment-like things
        content = re.sub(r'<!([^>]+)>', r'`<!\1>`', content)

        # Fix JSX-like expressions that aren't valid
        # Pattern: {text} not in code blocks
        # content = re.sub(r'(?<!`)(\{[^}]+\})(?!`)', r'`\1`', content)

        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
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

    print(f"Fixing MDX tags in {len(md_files)} files...")

    fixed = 0
    for md_file in md_files:
        if fix_mdx_tags(md_file):
            fixed += 1
            print(f"✓ Fixed {md_file.name}")

    print(f"\n✓ Fixed {fixed} files")


if __name__ == "__main__":
    main()
