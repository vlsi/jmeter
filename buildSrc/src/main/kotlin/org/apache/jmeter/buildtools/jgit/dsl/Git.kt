package org.apache.jmeter.buildtools.jgit.dsl

import org.eclipse.jgit.api.*

fun gitInit(action: InitCommand.() -> Unit) = Git.init().apply { action() }.call()

fun gitClone(action: CloneCommand.() -> Unit) = Git.cloneRepository().apply { action() }.call()

fun gitLsRemote(action: LsRemoteCommand.() -> Unit) = Git.lsRemoteRepository().apply { action() }.call()

fun Git.add(action: AddCommand.() -> Unit) = add().apply { action() }.call()
