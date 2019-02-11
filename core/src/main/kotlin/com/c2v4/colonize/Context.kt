package com.c2v4.colonize

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

val context = Kodein {
    bind() from singleton { Stage(FitViewport(640F, 480F)) }
    bind() from singleton { Skin(Gdx.files.internal("ui/skin.json")) }
    bind() from singleton { ColonizeGame() }
}
