#!/usr/bin/env python3
"""
Migrate all remaining XML documentation files to Markdown.
"""

import re
import shutil
from pathlib import Path
from convert_xml_to_markdown import XMLToMarkdownConverter


def migrate_single_file(xml_path: Path, output_path: Path, converter: XMLToMarkdownConverter):
    """Migrate a single XML file to Markdown."""
    try:
        result = converter.convert_document(xml_path)

        # Create frontmatter
        content = f"---\n"
        content += f"title: {result['title']}\n"
        content += f"---\n\n"
        content += result['content']

        # Ensure output directory exists
        output_path.parent.mkdir(parents=True, exist_ok=True)

        # Write file
        with open(output_path, "w", encoding="utf-8") as f:
            f.write(content)

        return True
    except Exception as e:
        print(f"Error migrating {xml_path}: {e}")
        return False


def migrate_usermanual(xdocs_dir: Path, docs_dir: Path, converter: XMLToMarkdownConverter):
    """Migrate user manual XML files."""
    usermanual_dir = xdocs_dir / "usermanual"
    output_dir = docs_dir / "docs" / "usermanual"

    output_dir.mkdir(parents=True, exist_ok=True)

    # Get all XML files except component_reference.xml (already split)
    xml_files = [
        f for f in usermanual_dir.glob("*.xml")
        if f.name != "component_reference.xml"
    ]

    migrated = 0
    for xml_file in xml_files:
        output_file = output_dir / f"{xml_file.stem}.md"
        if migrate_single_file(xml_file, output_file, converter):
            print(f"✓ Migrated {xml_file.name} -> {output_file.name}")
            migrated += 1

    return migrated


def migrate_root_docs(xdocs_dir: Path, docs_dir: Path, converter: XMLToMarkdownConverter):
    """Migrate root-level documentation files."""
    output_dir = docs_dir / "docs"

    # Files to migrate from root
    root_files = [
        "index.xml",
        "changes.xml",
        "building.xml",
        "extending.xml",
        "issues.xml",
        "security.xml",
        "mail.xml",
        "download_jmeter.xml",
        "creating-templates.xml",
        "devguide-dashboard.xml",
    ]

    migrated = 0
    for filename in root_files:
        xml_file = xdocs_dir / filename
        if xml_file.exists():
            # Map to appropriate output path
            if filename == "index.xml":
                output_file = output_dir / "index.md"
            else:
                output_file = output_dir / f"{xml_file.stem}.md"

            if migrate_single_file(xml_file, output_file, converter):
                print(f"✓ Migrated {filename} -> {output_file.name}")
                migrated += 1

    return migrated


def migrate_extending(xdocs_dir: Path, docs_dir: Path, converter: XMLToMarkdownConverter):
    """Migrate extending directory."""
    extending_dir = xdocs_dir / "extending"
    output_dir = docs_dir / "docs" / "extending"

    if not extending_dir.exists():
        return 0

    output_dir.mkdir(parents=True, exist_ok=True)

    # Get XML files
    xml_files = list(extending_dir.glob("*.xml"))

    migrated = 0
    for xml_file in xml_files:
        output_file = output_dir / f"{xml_file.stem}.md"
        if migrate_single_file(xml_file, output_file, converter):
            print(f"✓ Migrated {xml_file.name} -> {output_file.name}")
            migrated += 1

    # Copy PDFs
    for pdf_file in extending_dir.glob("*.pdf"):
        dest = output_dir / pdf_file.name
        shutil.copy2(pdf_file, dest)
        print(f"✓ Copied {pdf_file.name}")
        migrated += 1

    return migrated


def migrate_localising(xdocs_dir: Path, docs_dir: Path, converter: XMLToMarkdownConverter):
    """Migrate localising directory."""
    localising_dir = xdocs_dir / "localising"
    output_dir = docs_dir / "docs" / "localising"

    if not localising_dir.exists():
        return 0

    output_dir.mkdir(parents=True, exist_ok=True)

    # Get XML files
    xml_files = list(localising_dir.glob("*.xml"))

    migrated = 0
    for xml_file in xml_files:
        output_file = output_dir / f"{xml_file.stem}.md"
        if migrate_single_file(xml_file, output_file, converter):
            print(f"✓ Migrated {xml_file.name} -> {output_file.name}")
            migrated += 1

    return migrated


def main():
    """Main migration function."""
    xdocs_dir = Path("/home/user/jmeter/xdocs")
    docs_dir = Path("/home/user/jmeter/docs")
    converter = XMLToMarkdownConverter()

    print("=" * 60)
    print("Migrating JMeter Documentation to Markdown")
    print("=" * 60)

    total_migrated = 0

    print("\n1. Migrating root-level documentation...")
    total_migrated += migrate_root_docs(xdocs_dir, docs_dir, converter)

    print("\n2. Migrating user manual...")
    total_migrated += migrate_usermanual(xdocs_dir, docs_dir, converter)

    print("\n3. Migrating extending documentation...")
    total_migrated += migrate_extending(xdocs_dir, docs_dir, converter)

    print("\n4. Migrating localising documentation...")
    total_migrated += migrate_localising(xdocs_dir, docs_dir, converter)

    print("\n" + "=" * 60)
    print(f"✓ Migration complete! {total_migrated} files migrated.")
    print("=" * 60)


if __name__ == "__main__":
    main()
