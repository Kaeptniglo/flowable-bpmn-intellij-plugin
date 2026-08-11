package com.valb3r.bpmn.intellij.plugin.camunda

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.valb3r.bpmn.intellij.plugin.camunda.advertisement.CamundaAdvertisementState
import com.valb3r.bpmn.intellij.plugin.camunda.settings.CamundaBpmnPluginSettingsState
import com.valb3r.bpmn.intellij.plugin.core.advertisement.currentAdvertisementStateProvider
import com.valb3r.bpmn.intellij.plugin.core.settings.currentSettingsStateProvider
import java.util.concurrent.atomic.AtomicBoolean

class CamundaPluginPreloaded: ProjectActivity {

    private val isLoaded = AtomicBoolean()

    override suspend fun execute(project: Project) {
        if (isLoaded.compareAndSet(false, true)) {
            currentSettingsStateProvider.set { ApplicationManager.getApplication().getService(CamundaBpmnPluginSettingsState::class.java) }
            currentAdvertisementStateProvider.set { ApplicationManager.getApplication().getService(CamundaAdvertisementState::class.java) }
        }
    }
}