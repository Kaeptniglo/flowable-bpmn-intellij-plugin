[![Build Status](https://github.com/Kaeptniglo/flowable-bpmn-intellij-plugin/workflows/Plugin%20CI/badge.svg)](https://github.com/Kaeptniglo/flowable-bpmn-intellij-plugin/actions)

[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/banner2-direct.svg)](https://stand-with-ukraine.pp.ua)

# About this fork

This is a fork of [valb3r/flowable-bpmn-intellij-plugin](https://github.com/valb3r/flowable-bpmn-intellij-plugin)
by Valentyn Berezin, updated to run on **IntelliJ IDEA 2026.2** (build 262).

Upstream's last release (0.5.4.4) targets IntelliJ 2024.3 and does not install on 2026.2.
This fork carries version **0.6.0-2026.2** so the two builds are easy to tell apart in the
plugin list. It is **not** published on the JetBrains Marketplace — build it yourself as
described under [Building from sources](#building-from-sources).

### What changed relative to upstream

| | upstream 0.5.4.4 | this fork |
|---|---|---|
| Target IntelliJ Platform | 2024.3.5 | 2026.2.1 |
| `since-build` | 233 | 262 |
| Java | 17 | **25** |
| Gradle | 8.12 | 9.7.0 |
| Kotlin | 2.1.0 | 2.4.10 |
| IntelliJ Platform Gradle Plugin | 2.4.0 | 2.18.1 |

IntelliJ 2026.2 ships class files with major version 69, so the plugin has to be compiled
with **JDK 25**; JDK 17 or 21 will not work. The IDE bundles Kotlin 2.4, which is why the
Kotlin `apiVersion` is pinned to 2.2.

Source changes were limited to APIs that were removed or repackaged:
`ServiceManager` was replaced by `project.getService()` /
`ApplicationManager.getApplication().getService()`, `StartupActivity.Background` by
`ProjectActivity`, and `groovy.lang.Tuple2` by `kotlin.Pair` (Groovy is no longer on the
plugin classpath). Feature behaviour is unchanged.

### Status

The plugin builds, passes the test suite and the IntelliJ Plugin Verifier reports
`Compatible` against IU-262.9437.185. It loads and opens diagrams in IntelliJ 2026.2.1.
The deeper features (editing, undo/redo, code navigation, process debugging) have had
little manual testing on 2026.2 so far — the automated tests mostly cover the XML parsers,
not the UI. Please report anything that misbehaves.

### License and attribution

MIT, unchanged from upstream. Copyright remains with Valentyn Berezin for the original
work; see [LICENSE](LICENSE). The license file is bundled into every plugin Jar under
`META-INF/LICENSE`.

---

# What is this

This project provides BPMN modeler plugin for these BPMN-engines:
 - [Flowable BPMN engine](https://github.com/flowable/flowable-engine)
 - [Activiti BPMN engine](https://github.com/Activiti/Activiti)
 - [Camunda BPMN engine](https://github.com/camunda/camunda-bpm-platform)

Key goals are: process editing integration into IntelliJ, code navigation support between BPMN diagram and your classes including Spring beans.

**Currently, it is work-in-progress.**


# Key features

1. BPMN process editing (BPMN modeler) - adding/removing elements, changing their properties, undo/redo, bulk drag-n-drop, bulk removal of elements
1. Code navigation - jump from `Delegate Expression` **(IntelliJ Ultimate)** or `Class` **(IntelliJ Community)** property directly to bean/function/class in code
1. IntelliJ refactorings propagation to backing XML file of the process (i.e. rename bean) **(IntelliJ Ultimate)**
1. Jump from an element to the underlying XML **(IntelliJ Ultimate)**
1. BPMN process 'debugging' by allowing to see steps (and their order) done for latest process execution directly in plugin **(IntelliJ Ultimate)**


# Installation

**NOTE: The plugin requires 'Ultimate Edition' of IntelliJ for code navigation**

This fork is not on the JetBrains Marketplace. Get a ZIP in one of three ways:

- **Download the prebuilt ZIP from this repository** — no JDK needed:
  [`dist/flowable-bpmn-plugin-0.6.0-2026.2.zip`](dist/flowable-bpmn-plugin-0.6.0-2026.2.zip)
  (13.5 MB, Flowable only)

  ```
  SHA-256  7ba2c27baaef5b048eaf29d62e8a0d6d81f6efa5f6e2422f533995c384042192
  ```

  Verify it with `sha256sum` (Linux/macOS) or
  `Get-FileHash <file> -Algorithm SHA256` (Windows PowerShell).

- **Build it yourself** — see [Building from sources](#building-from-sources) below.
  Required for the Activiti and Camunda variants, which are not checked in.
- **Download a CI build** — open the latest green run of the `Plugin CI`
  [workflow](https://github.com/Kaeptniglo/flowable-bpmn-intellij-plugin/actions) and grab
  the `plugin-distributions` artifact. This one contains all three plugins.

Then install it:

1. Open `File -> Settings -> Plugins`
1. Click the gear icon -> `Install Plugin from Disk...`
1. Select the ZIP and restart the IDE

If a previous build of the plugin is already installed, **uninstall it first**. Installing
over an existing copy can leave stale jars behind in the plugin directory.

> The Marketplace entries for
> [`Flowable BPMN visualizer`](https://plugins.jetbrains.com/plugin/14318-flowable-bpmn-visualizer),
> [`Activiti BPMN visualizer`](https://plugins.jetbrains.com/plugin/15222-activiti-bpmn-visualizer)
> and [`Camunda BPMN visualizer`](https://plugins.jetbrains.com/plugin/17844-camunda-bpmn-visualizer)
> serve the **upstream** plugin, which does not support IntelliJ 2026.2. Do not install
> both at the same time.


# Building from sources

## Prerequisites

- **JDK 25** — required. IntelliJ 2026.2 class files are Java 25, so JDK 17 or 21 fail to
  compile against the platform.
- Nothing else. The Gradle wrapper fetches Gradle 9.7.0, and the build downloads the
  IntelliJ IDEA Ultimate distribution it compiles against (a few GB on the first run).

Point Gradle at your JDK 25 by setting `JAVA_HOME`, for example:

```shell script
export JAVA_HOME=/path/to/jdk-25          # Linux / macOS
$env:JAVA_HOME = "C:\Program Files\Amazon Corretto\jdk25.0.4_7"   # Windows PowerShell
```

Opening the project in IntelliJ: set **Settings -> Build, Execution, Deployment -> Build
Tools -> Gradle -> Gradle JVM** to JDK 25.

## Build

```shell script
./gradlew clean buildPlugin
```

This builds all three plugins. The distributions are written to:

| Engine | ZIP |
|---|---|
| Flowable | `flowable-intellij-plugin/build/distributions/flowable-intellij-plugin.zip` |
| Activiti | `activiti-intellij-plugin/build/distributions/activiti-intellij-plugin.zip` |
| Camunda  | `camunda-intellij-plugin/build/distributions/camunda-intellij-plugin.zip` |

To build only one of them, prefix the task with the module, e.g.
`./gradlew clean :flowable-intellij-plugin:buildPlugin`.

Install the result by following the steps under [Installation](#installation).

### Refreshing the checked-in ZIP

[`dist/`](dist) holds a prebuilt Flowable ZIP so colleagues without a JDK 25 can install
the plugin without building it. It is **not** produced by the build and does not update
itself — after changing plugin code, refresh it and update the checksum in this README:

```shell script
./gradlew :flowable-intellij-plugin:buildPlugin
cp flowable-intellij-plugin/build/distributions/flowable-intellij-plugin.zip \
   dist/flowable-bpmn-plugin-<version>.zip
sha256sum dist/flowable-bpmn-plugin-<version>.zip
```

Keep in mind that every refresh adds another ~13.5 MB blob to the git history for good.
If this starts to hurt, attach the ZIP to a GitHub release instead and link that here.

## Run in a sandbox IDE

To try the plugin without installing it into your own IDE:

**Flowable:**

```shell script
./gradlew clean :flowable-intellij-plugin:runIde
```

**Activiti:**

```shell script
./gradlew clean :activiti-intellij-plugin:runIde
```

**Camunda:**

```shell script
./gradlew clean :camunda-intellij-plugin:runIde
```

## Tests and compatibility check

```shell script
./gradlew test
./gradlew :flowable-intellij-plugin:verifyPlugin
```

`verifyPlugin` runs the official IntelliJ Plugin Verifier against the target IDE build and
takes a couple of minutes.

## Sample BPMN files

[`samples/`](samples) holds a handful of ready-to-open BPMN files with diagram coordinates,
including a small hand-written one to start with. See [samples/README.md](samples/README.md).


# Workflow

## Plugin usage:

The videos below were recorded against the upstream plugin; the UI is unchanged.

### Basic usage

[![Work with plugin](https://img.youtube.com/vi/8-_XmOlEyXM/0.jpg)](https://youtu.be/8-_XmOlEyXM)


### Adding new elements

[![Work with plugin](https://img.youtube.com/vi/cyLbEeaMDvI/0.jpg)](https://youtu.be/cyLbEeaMDvI)


### BPMN-Java-XML 'gluing' usage (IntelliJ Ultimate, only classes for IntelliJ Community)

[![Work with plugin](https://img.youtube.com/vi/BQf0eglY2vo/0.jpg)](https://youtu.be/BQf0eglY2vo)


### Debugging BPMN process with the plugin

[![Debug BPMN with plugin](https://img.youtube.com/vi/_zQ1zy_0Qfc/0.jpg)](https://youtu.be/_zQ1zy_0Qfc)


# Navigation/editing guideline

1. To open BPMN diagram in the plugin - left mouse button on XML file and select `View BPMN Diagram`
1. To move diagram up/down/left/right - click mouse wheel and start moving your mouse - diagram will follow 
(like dragging with mouse wheel) or Shift + Left Mouse Button
1. Zoom in/out - mouse wheel rotation
1. To **add a new element** - click with right mouse button and popup menu with new element selection will appear 
1. To **Copy or cut element(s)** - select elements you want to copy/paste and click with right mouse button on them to 
see popup menu, there select cut or copy menu item
1. To **Paste element(s)** - (copy/cut before) click with right mouse button on the desired location and 
select 'Paste' popup menu item
1. To select element click on it with mouse


# FAQ

### Q: My files for Activiti/Flowable engine have `.bpmn` extension and not `bpmn20.xml`, how can I open them.

**A**: Navigate to **File** > **Settings** > **Tools** > **Activiti BPMN Plugin config** (or **Flowable BPMN Plugin config**). 
In the field **Supported extensions** add `bpmn`, so that field value is `bpmn20.xml,bpmn`. Now you should be able to open it.
[Animation to configure plugin for opening files with custom extension](docs/img/faq/how-to-open-bpmn.gif)


### Q: Where do I report a problem?

**A**: For anything specific to IntelliJ 2026.2 support, use this fork's
[issue tracker](https://github.com/Kaeptniglo/flowable-bpmn-intellij-plugin/issues).
For questions about the plugin's features in general, the
[upstream project](https://github.com/valb3r/flowable-bpmn-intellij-plugin) and its
[Gitter channel](https://gitter.im/flowable-bpmn-intellij-plugin/community) are the better
place.


# Technical details


## Architectural diagrams

### Plugin modules

![Modules diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/valb3r/flowable-bpmn-intellij-plugin/master/docs/img/module_architecture.puml&fmt=svg&vvv=3&sanitize=true)


# Flowable XML subset support

<details><summary><b>View summary table</b></summary>


|            XML element           | Supported |
|:--------------------------------:|:---------:|
|          adHocSubProcess         |     Y     |
|            association           |           |
|           boundaryEvent          |     P     |
|        bpmndi:BPMNDiagram        |     Y     |
|          bpmndi:BPMNEdge         |     Y     |
|         bpmndi:BPMNPlane         |     Y     |
|         bpmndi:BPMNShape         |     Y     |
|         businessRuleTask         |     Y     |
|           callActivity           |     Y     |
|       cancelEventDefinition      |     P     |
|     compensateEventDefinition    |     P     |
|        completionCondition       |     P     |
|             condition            |     P     |
|    conditionalEventDefinition    |     P     |
|        conditionExpression       |     Y     |
|            dataObject            |           |
|            definitions           |           |
|           documentation          |     Y     |
|             endEvent             |     Y     |
|       errorEventDefinition       |     P     |
|     escalationEventDefinition    |     P     |
|         eventBasedGateway        |     P     |
|         exclusiveGateway         |     Y     |
|         extensionElements        |           |
|      flowable:eventListener      |           |
|    flowable:executionListener    |           |
|          flowable:field          |     Y     |
|            flowable:in           |           |
|           flowable:out           |           |
|          flowable:string         |     Y     |
|          flowable:value          |     Y     |
|         inclusiveGateway         |     Y     |
|      intermediateCatchEvent      |     P     |
|      intermediateThrowEvent      |     P     |
|          loopCardinality         |           |
|              message             |           |
|      messageEventDefinition      |           |
| multiInstanceLoopCharacteristics |           |
|           omgdc:Bounds           |     Y     |
|          omgdi:waypoint          |     Y     |
|          parallelGateway         |     P     |
|              process             |     P     |
|            receiveTask           |     P     |
|              script              |     Y     |
|            scriptTask            |     Y     |
|           sequenceFlow           |     Y     |
|            serviceTask           |     Y     |
|       signalEventDefinition      |     P     |
|            startEvent            |     Y     |
|            subProcess            |     Y     |
|     terminateEventDefinition     |     P     |
|               text               |     P     |
|          textAnnotation          |     P     |
|             timeDate             |     P     |
|       timerEventDefinition       |     P     |
|            transaction           |     Y     |
|             userTask             |     Y     |

**Legend**:

**Y** - Mostly or fully supported

**P** - Partially supported

**Blank** - Mostly unsupported

</details>
