---
title: JMeter Localisation (Translator's Guide)
---

# Introduction

This document describes the process of creating and maintaining translated texts for JMeter in languages
other than English. English has been tacitly chosen as the project's primary (or "default") language – despite its
obvious inadequacy for reasonably unambiguous communication – as a tribute to the Power of the Empire :-)  

The metropolitan language texts are thus maintained by the software developers, while other project contributors
(called "translators" in this document) take care of maintaining the texts in the languages of the
provinces. The process of producing and maintaining the later is called "translation" in this document.

This document assumes you'll be using i18nEdit as your tool to edit properties files, and instructions will
be specific to this software, but this is not mandatory: the process should mostly work also if you prefer to use
another tool, such as or **vi** or **Emacs**.

This document describes 6 processes:

1. Obtaining the current texts [translators].
2. Providing the current texts to translators [developers].
3. Downloading and running i18nEdit [everyone].
4. Translating [translators].
5. Submitting your translations to the project [translators].
6. Merging in new translations [committers].

# Obtaining the current texts

If you want to help with JMeter's translation process, start by reading this document. Then
send a message to [dev@jmeter.apache.org](mailto:dev@jmeter.apache.org)
stating your intention. The files you need (*.properties and *.metaprop) are included in the source archive.
But if you are having any difficulty, one of the project contributors will be able to grab the current texts
from SVN and send them to you. You'll receive a jar, zip, tar or tgz file that you'll need to unpack in your
local disk.

If you are familiar with SVN or you're brave, feel free to anonymously connect to the Apache SVN server
and obtain the JMeter source yourself, as described in
[http://jmeter.apache.org/svnindex.html](http://jmeter.apache.org/svnindex.html)
– the files necessary to the translation process are all under the `jmeter/src` directory.

Once you've unpacked or checked out the files, make sure to find file `src/i18nedit.properties` in there:
you'll need to know where it is to start working with i18nEdit.

# Providing the current texts to translators

If you have access to JMeter's SVN repository and you want to pack the files necessary for localisation
for sending to a translator, just go to the directory above the project root and issue the following command:

```

tar czf jmeter-localisation.tgz `find jmeter/src -name "*.properties" -o -name "*.metaprops"`

```

Of course you could also send the translator the whole jmeter directory, but this will make their life easier.

# Downloading and running i18nEdit

The runtime for i18nEdit can be obtained from
[https://sourceforge.net/projects/i18nedit/](https://sourceforge.net/projects/i18nedit/).
Download the binary distribution (i18nedit-1.2.2.jar) and save it locally.

To run i18nEdit, just make sure to have a reasonably modern Java Runtime Environment in your PATH, change
to the directory where you saved i18nedit-1.2.2.jar, then issue the following command:

```

java -jar i18nedit-1.2.2.jar

```

Then:1. If you've never run i18nEdit before, choose a language. The rest of this document assumes you chose UK English.
2. Select the "`Projects`" menu, then "`Open project…`".
3. Navigate to `jmeter/src/`, select `i18nedit.properties`, and press the "`Open`" button.
4. In the window that opens, select the "`Project`" menu, then "`Project settings`". Check that your target language
appears in the list in field "`Additional locales (ISO codes)`". Otherwise, add it now. Press "`Save`".

You're now ready to start translating.# Translating

Before you start translating, select the "`Project`" menu, then "`Translation settings`". Choose work mode
"`Directed translation (source to target)`". Enter "`en`" (without the quotes) in the "`Source localization`" field. Enter
the ISO code of your target language in the "`Target localization field`".

Click on one of the editable fields in the right panel ("`Comment`" or "`Content`" for your language). Press F2.
i18nEdit will bring you to the first property that requires your attention, either because a translation does not yet
exist for it or because the English text has changed since the translation was provided. Enter or fix the text if
necessary, then press F2 again to repeat the process.

i18nEdit's on-line help is excellent: read through it for more information and tips.

# Submitting your translations to the project

Once you're done translating, just pack up the whole set of files in `jmeter/src` in a jar, zip, tar,
tgz, or alike and attach them to a JMeter bug report
(follow link to "Known bugs" in [JMeter's home page](http://jmeter.apache.org/) for that).

# Merging in new translations

1. Unpack the files submitted by the translator in a separate directory.
2. Start i18nEdit as described in [Downloading and running i18nEdit](#i18nEdit) above.
3. If the translator worked in a new language, make sure it is listed in the Additional locales field in the Project Settings.
4. Open the "`Team`" menu and select "`Merge changes as integrator`".
5. Enter the path to the `src` directory in the files submitted by the translator.
6. Select the translator's target language.
7. Press "`Perform merge`".
8. Close i18nEdit and commit to SVN as usual (remember to Refresh your project if you're using Eclipse).

