# BPMN-Beispieldateien

Dateien zum manuellen Testen des Plugins. Rechtsklick auf eine Datei →
**View BPMN (Flowable) Diagram**, oder das Tool-Window `BPMN-Flowable-Diagram`
rechts öffnen.

Alle Dateien enthalten BPMN-Diagram-Interchange (Koordinaten), werden also
tatsächlich gezeichnet und lassen sich im Editor verschieben.

## flowable/

| Datei | Elemente | Wozu |
|---|---|---|
| `order-approval.bpmn20.xml` | 14 | Handgeschriebener, überschaubarer Prozess: Start → User Task → XOR-Gateway mit zwei Bedingungen → Service Tasks → Ende. Guter Einstieg. |
| `collapsed-subprocess.bpmn20.xml` | 7 | Eingeklappter Subprozess, eigene BPMNPlane. |
| `http-service-task.bpmn20.xml` | 2 | Flowable HTTP-Task mit `extensionElements`/Feldern. |
| `boundary-events-and-timer.bpmn20.xml` | 24 | Boundary Events und Timer in allen Varianten. |
| `nested-subprocesses.bpmn20.xml` | 34 | Mehrfach verschachtelte Subprozesse. |
| `showcase-all-elements.bpmn20.xml` | 97 | Großes Sammelsurium fast aller unterstützten Elemente — gut für Rendering- und Performance-Eindruck, unübersichtlich zum Editieren. |

`order-approval.bpmn20.xml` ist neu geschrieben. Die übrigen fünf sind Kopien
aus `flowable-xml-parser/src/test/resources/` (dort liegen ~37 weitere
Parser-Fixtures, falls mehr Fälle gebraucht werden) und stehen unter der
Lizenz dieses Repositories.

## Sprung ins Java-Coding testen

`order-approval.bpmn20.xml` enthält bewusst beide Varianten:

- `flowable:delegateExpression="${bookOrderDelegate}"` — Sprung auf einen Bean
- `flowable:class="com.example.order.NotifyCustomerDelegate"` — Sprung auf eine Klasse

Beide Ziele existieren im Projekt nicht. Damit die Navigation etwas zu finden
hat, muss eine entsprechende Klasse bzw. ein Bean im geöffneten Projekt liegen.
