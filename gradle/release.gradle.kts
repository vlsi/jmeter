import org.apache.jmeter.buildtools.release.ReleaseExtension
import org.apache.jmeter.buildtools.release.StageVoteReleasePlugin
import org.apache.jmeter.buildtools.release.ReleaseParams
import org.apache.jmeter.buildtools.release.RepositoryType

/**
 * Release steps:
 *  1. Create artifacts
 *  2. initializeNexusStagingRepository
 *  3. Save repository id to stagingRepositoryId_txt==$buildDir/
 *  4. Publish to Maven (stage)
 *  5. Publish to dist.apache.org
 *  6.
 */

apply<StageVoteReleasePlugin>()

configure<ReleaseExtension> {
    tlp.set("JMeter")
    voteText.set { it.voteTextGen() }
    svnDist {
        releaseSubfolder.apply {
            put(Regex("_src\\."), "sources")
            put(Regex("."), "binaries")
        }
    }
    nexus {
        username.set(project.property("asfNexusUsername").toString())
        password.set(project.property("asfNexusPassword").toString())
        if (repositoryType.get() == RepositoryType.PROD) {
            stagingProfileId.set("4d29c092016673")
        }
    }
}

fun ReleaseParams.voteTextGen(): String = """
The first release candidate for JMeter $version ($shortGitSha) has been
prepared, and your votes are solicited.

This release is mainly a bugfix

Please, test this release candidate (with load tests and/or functional
tests) using Java 8+ on Linux/Windows/macOS, especially on the changes.
Feedback is very welcome within the next 72 hours.

You can read the New and Noteworthy section with some screenshots to
illustrate improvements and full list of changes at:
http://home.apache.org/~milamber/$tlpUrl-$tag/docs/changes.html

JMeter is a Java desktop application designed to load test functional
behavior and measure performance. The current version targets Java 8+

Download - Archives/hashes/sigs:

$svnStagingUri
(dist revision TBD:SVN revision of svnmucc stage result)

RAT report:

http://home.apache.org/~milamber/$tlpUrl-$tag/dist/rat-report-$tlpUrl-$tag.txt

SHA512 hashes of archives for this vote: see footnote [1]

Site Docs are here:
http://home.apache.org/~milamber/$tlpUrl-$tag/docs/

Maven staging repository is accessible here:
$nexusRepositoryUri/org/apache/$tlpUrl/

Tag:
https://svn.apache.org/repos/asf/$tlpUrl/tags/$tag/

Keys are here:
https://www.apache.org/dist/$tlpUrl/KEYS

N.B.
To create the jars and test $tlp: "./gradlew build".

$tlp $version requires Java 8 or later to run.

Some known issues and incompatible changes are listed on changes page.
http://home.apache.org/~milamber/$tlpUrl-$tag/docs/changes.html#Known%20problems%20and%20workarounds


All feedback and vote are welcome.

[  ] +1  I support this release
[  ] +0  I am OK with this release
[  ] -0  OK, but....
[  ] -1  I do not support this release (please indicate why)

The vote will remain open for at least 72 hours.

The PMC members please indicate the mention "(binding)" with your vote.


Note: If the vote passes, the intention is to release the archive files
and rename the RC tag as the release tag.

Thanks in advance!

Milamber


===
[1] SHA512 hashes of archives for this vote:

${artifacts.joinToString(System.lineSeparator()) { it.sha512 + System.lineSeparator() + "*" + it.name }}
""".trimIndent()
