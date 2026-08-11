package com.valb3r.bpmn.intellij.plugin.flowable

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.valb3r.bpmn.intellij.plugin.core.advertisement.currentAdvertisementStateProvider
import com.valb3r.bpmn.intellij.plugin.core.settings.currentSettingsStateProvider
import com.valb3r.bpmn.intellij.plugin.flowable.advertisement.FlowableAdvertisementState
import com.valb3r.bpmn.intellij.plugin.flowable.settings.FlowableBpmnPluginSettingsState
import java.util.concurrent.atomic.AtomicBoolean

class FlowablePluginPreloaded: ProjectActivity {

    private val isLoaded = AtomicBoolean()

    override suspend fun execute(project: Project) {
        if (isLoaded.compareAndSet(false, true)) {
            currentSettingsStateProvider.set { ApplicationManager.getApplication().getService(FlowableBpmnPluginSettingsState::class.java) }
            currentAdvertisementStateProvider.set { ApplicationManager.getApplication().getService(FlowableAdvertisementState::class.java) }
        }
    }
}