package com.valb3r.bpmn.intellij.activiti.plugin

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.valb3r.bpmn.intellij.activiti.plugin.advertisement.ActivitiAdvertisementState
import com.valb3r.bpmn.intellij.activiti.plugin.settings.ActivitiBpmnPluginSettingsState
import com.valb3r.bpmn.intellij.plugin.core.advertisement.currentAdvertisementStateProvider
import com.valb3r.bpmn.intellij.plugin.core.settings.currentSettingsStateProvider
import java.util.concurrent.atomic.AtomicBoolean

class ActivitiPluginPreloaded: ProjectActivity {

    private val isLoaded = AtomicBoolean()

    override suspend fun execute(project: Project) {
        if (isLoaded.compareAndSet(false, true)) {
            currentSettingsStateProvider.set { ApplicationManager.getApplication().getService(ActivitiBpmnPluginSettingsState::class.java) }
            currentAdvertisementStateProvider.set { ApplicationManager.getApplication().getService(ActivitiAdvertisementState::class.java) }
        }
    }
}