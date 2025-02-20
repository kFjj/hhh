# 类图设计报告

## 1. 类图绘画过程

### 1.1 确定核心概念

首先，我分析了系统的功能需求，识别出几个关键的概念：`Main`、`Game`、`Parser`、`CommandWords`、`Command` 和 `Room`。这些概念是构成游戏逻辑的基础。

### 1.2 定义类与接口

接下来，我为每个概念定义了相应的类或接口，并明确了它们的主要属性和方法：

- **`Main` 类**:
  - **方法**: 
    - `main(String[] args)`: 应用程序入口点，创建 `Game` 类的一个实例并调用其 `play()` 方法来启动游戏。

- **`Game` 类**:
  - **成员变量**:
    - `parser: Parser`: 解析用户命令的对象。
    - `currentRoom: Room`: 当前玩家所在的房间。
  - **方法**:
    - `Game()`: 构造函数，初始化游戏环境。
    - `play()`: 游戏主循环，处理用户输入直到游戏结束。
    - `printWelcome()`: 打印欢迎信息和当前房间的描述。
    - `processCommand(Command command)`: 处理用户输入的命令。
    - `printHelp()`: 显示帮助信息。
    - `goRoom(Command command)`: 根据用户输入的方向移动到下一个房间。
    - `quit(Command command)`: 处理退出游戏的请求。
    - `createRooms()`: 创建所有房间并将它们连接成迷宫结构（私有方法）。

- **`Parser` 类**:
  - **成员变量**:
    - `commands: CommandWords`: 管理有效命令词汇的对象。
    - `reader: Scanner`: 读取用户输入的对象。
  - **方法**:
    - `Parser()`: 构造函数，初始化命令词汇表和输入读取器。
    - `getCommand()`: 从用户输入中解析出命令并返回一个 `Command` 对象。
    - `showCommands()`: 显示所有可用命令。

- **`Room` 类**:
  - **成员变量**:
    - `description: String`: 房间的简短描述。
    - `exits: HashMap<String, Room>`: 存储房间出口及其对应相邻房间的映射。
  - **方法**:
    - `Room(String description)`: 构造函数，初始化房间描述和出口映射。
    - `setExit(String direction, Room neighbor)`: 设置某个方向上的出口。
    - `getShortDescription()`: 返回房间的简短描述。
    - `getLongDescription()`: 返回房间的详细描述，包括所有出口。
    - `getExitString()`: 构建并返回所有出口方向的字符串（私有方法）。
    - `getExit(String direction)`: 根据给定方向获取相邻房间对象。

- **`Command` 类**:
  - **成员变量**:
    - `commandWord: String`: 命令词。
    - `secondWord: String`: 命令的第二个单词（参数）。
  - **方法**:
    - `Command(String firstWord, String secondWord)`: 构造函数，初始化命令词和参数。
    - `getCommandWord()`: 获取命令词。
    - `getSecondWord()`: 获取命令的第二个单词。
    - `isUnknown()`: 检查命令是否已知。
    - `hasSecondWord()`: 检查命令是否有第二个单词。

- **`CommandWords` 类**:
  - **成员变量**:
    - `validCommands: String[]`: 存储所有有效命令词汇的数组。
  - **方法**:
    - `CommandWords()`: 构造函数，目前为空。
    - `isCommand(String aString)`: 检查给定字符串是否为有效命令。
    - `showAll()`: 显示所有可用命令。

### 1.3 描述类间关系

在确定了各个类的定义后，我进一步描述了它们之间的关系，以确保系统各部分能够协同工作：

- **`Main` 类**:
  - 创建并启动 `Game` 类，通过调用 `game.play()` 来开始游戏流程。

- **`Game` 类**:
  - 使用 `Parser` 类来解析用户的命令输入。
  - 管理多个 `Room` 实例，构建游戏世界，并根据玩家命令更新当前房间。
  - 调用 `CommandWords` 类的方法来验证命令的有效性，并通过 `Command` 对象处理玩家指令。

- **`Parser` 类**:
  - 依赖于 `CommandWords` 类来检查用户输入是否为有效命令。
  - 创建 `Command` 对象，并将它们传递给 `Game` 类进行处理。

- **`Room` 类**:
  - 通过 `setExit()` 和 `getExit()` 方法与其他 `Room` 实例相连，形成房间网络。
  - 提供详细的房间描述，由 `Game` 类调用以显示当前房间信息。

- **`Command` 类**:
  - 封装用户输入的命令及其可能的参数，使 `Game` 类能够方便地解析和执行这些命令。

- **`CommandWords` 类**:
  - 与 `Parser` 协作，确保用户输入的是合法命令。
  - 在需要时向用户提供帮助信息，展示所有可用命令。

### 1.4 绘制图形表示

最后，我使用绘图工具mermdia来创建一个直观的类图，该图展示了上述所有信息。

## 2. 类图展示

下面是最终完成的类图：

![Class Diagram](/class.png)


# 代码可扩展性修改详情

### Command.java

- **功能**：封装用户输入的命令及其可能的参数。
- **修改**：保持原有结构，未做改动。

### CommandWords.java

- **功能**：管理和验证游戏中所有有效命令。
- **修改**：
  - 添加了新的命令词汇（如 `look`, `take`）。
  - 优化了遍历逻辑，使用静态数组存储命令词汇。

### Game.java

- **功能**：创建游戏并初始化内部数据和解析器，管理游戏循环。
- **修改**：
  - 引入了 `CommandDispatcher` 来管理命令。
  - 添加了新的命令类（如 `LookCommand`, `TakeCommand`）。
  - 改进了当前房间的管理和交互方式，提供了 `getCurrentRoom` 和 `setCurrentRoom` 方法。

### 新增文件

#### CommandDispatcher.java

- **功能**：负责注册和处理命令。
- **实现**：
  - 使用 `HashMap` 存储命令词与命令类的映射关系。
  - 提供 `registerCommand` 和 `processCommand` 方法，用于注册命令和处理用户输入。可以智能的根据用户的输入选择构造函数

#### GameCommand.java

- **功能**：定义了执行命令的方法。
- **实现**：
  - 创建接口 `GameCommand`，规定所有具体命令类必须实现 `execute(Game game)` 方法。

#### 具体命令类

- **GoCommand.java**
  - 功能：处理玩家移动到相邻房间的命令。
- **QuitCommand.java**
  - 功能：处理退出游戏的命令。
- **HelpCommand.java**
  - 功能：显示所有可用命令的帮助信息。
- **LookCommand.java**
  - 功能：显示当前房间的详细描述。
- **TakeCommand.java**
  - 功能：处理玩家拾取物品的命令。

## 实现步骤

1. **分析现有代码**：理解每个类的功能和相互关系。
2. **引入命令模式**：在 `Game` 类中引入 `CommandDispatcher`，并添加具体的命令类。
6. **编写文档**：记录所有修改和新增功能，便于后续开发和维护。

## 测试

为了确保修改后的代码能够正常运行，我们进行了以下测试：

- **基本命令测试**：测试 `go`, `quit`, `help`, `look`, `take` 等命令是否按预期工作。
- **边界条件测试**：检查没有提供第二单词或无效命令的情况。

## 结论

通过引入命令模式设计，我们不仅提高了代码的可维护性和扩展性。




# 扩展游戏功能：支持房间内存放多个物品并查看物品信息

## 概述

为了增强玩家的游戏体验，我们对游戏进行了扩展，使得每个房间可以存放任意数量的物品，并且玩家可以通过“look”命令查看当前房间的信息以及房间内的所有物品。本报告将详细介绍这一功能的实现过程，包括设计决策、功能实现和用户交互。

## 实现过程

### 步骤 1: 设计物品模型

#### 方法

我们定义了一个新的数据结构来表示物品，它包含了三个主要属性：
- **名称**：用于标识物品的唯一名称。
- **描述**：提供关于物品的更多信息，帮助玩家理解物品的功能或背景故事。
- **重量**：反映物品的实际物理特征，可能影响玩家携带物品的能力。

此外，我们还为这个结构添加了方法，以便于在程序中轻松获取和显示这些信息。

### 步骤 2: 修改房间模型以支持多物品存储


#### 方法

我们调整了房间的数据结构，使其能够保存一组物品。选择列表（list）作为存储方式，因为它允许灵活地添加新物品或删除现有物品。同时，我们为房间增加了几个重要的操作：
- **添加物品**：当某个物品被放置到房间时，我们可以调用此方法将其加入到物品列表中。
- **获取所有物品**：提供一个方法来访问房间中的所有物品，这对于展示给玩家非常重要。
- **检查房间是否为空**：有时候我们需要知道房间是否有任何物品，这有助于决定是否向玩家提示房间是空的。

### 步骤 3: 更新房间描述逻辑



#### 方法

我们更新了生成房间描述的方法，使得它现在会遍历房间中的所有物品，并将它们的信息整合到最终输出的文本中。具体来说：
- 如果房间内有物品，我们会列出每个物品的名称、描述和重量，使玩家能够清晰地看到房间里的东西。
- 如果房间内没有物品，我们会给出一条消息告诉玩家房间是空的，避免误导玩家认为房间内隐藏着未提及的物品。

### 步骤 4: 修改“look”命令的行为

#### 方法

我们调整了“look”命令的功能，使其不再仅仅返回房间的基础描述，而是调用更新后的房间描述逻辑，全面展示房间及其内部物品的信息。这一步骤确保了玩家每次使用“look”命令都能得到最新、最准确的环境信息。



## 总结

本次功能扩展提升了游戏的沉浸感和可玩性。通过设计物品模型、修改房间模型以支持多物品存储、更新房间描述逻辑以及调整“look”命令行为，我们成功实现了玩家可以在游戏中查看房间及其内部物品的功能。希望这份报告能帮助理解实现这一功能的过程。



# 实现“back”命令功能

## 概述

为了增强玩家的游戏体验，我们对游戏进行了扩展，实现了“back”命令。玩家输入该命令后，可以返回到上一个房间。本部分将详细介绍这一功能的实现过程，包括设计决策、功能实现和用户交互。

## 设计与实现过程

### 1. 设计回溯机制

#### 方法

我们选择了栈（stack）数据结构来保存玩家访问过的房间历史。栈是一种后进先出（LIFO, Last In First Out）的数据结构，非常适合用于追踪玩家的历史路径。每当玩家进入一个新的房间时，当前房间会被添加到栈中；当玩家使用“back”命令时，最近访问的房间会从栈中移除，并将玩家移至该房间。

### 2. 修改游戏逻辑以支持房间历史


#### 方法

我们在游戏的核心逻辑中引入了一个栈来保存房间历史记录。每当玩家通过命令（如“go”）进入新房间时，系统会检查当前房间是否与即将进入的房间不同。如果不同，则将当前房间添加到历史栈中。此外，我们还添加了一个方法来处理“back”命令，它会从历史栈中取出最近访问的房间，并将玩家移至该房间。如果玩家已经到达了起点（即栈为空），则不允许进一步回退。

### 3. 创建“back”命令处理器



#### 方法

我们创建了一个新的命令处理器，专门负责处理“back”命令。这个处理器会调用游戏逻辑中的方法，将玩家送回到历史路径中的上一个房间。同时，它还会提供用户反馈，告诉玩家他们回到了哪个房间或者提示无法再回退。

### 4. 修改命令分派器

#### 方法

我们在命令分派器中注册了“back”命令，使得它可以被命令解析器识别和处理。这一步骤确保了当玩家输入“back”命令时，系统能够正确调用相应的命令处理器。

### 5. 更新命令词管理器

#### 方法

我们在命令词管理器中添加了“back”命令，使其成为有效命令之一。这样，命令解析器就可以识别并处理“back”命令。

### 6. 更新帮助信息


#### 方法

我们更新了游戏的帮助信息，增加了对“back”命令的描述。这样，当玩家需要了解所有可用命令时，他们会看到有关如何使用“back”命令的信息。



## 总结

本次功能扩展提升了游戏的导航便利性和用户体验。通过设计回溯机制、修改游戏逻辑以支持房间历史、创建“back”命令处理器、修改命令分派器、更新命令词管理器以及更新帮助信息，我们成功实现了玩家可以在游戏中使用“back”命令返回上一个房间的功能。希望这份报告能帮助理解实现这一功能的过程。





# 实现传送门房间功能

## 概述

为了增强游戏的趣味性和探索性，我们在游戏中增加了一种特殊的房间类型——传送门房间。每当玩家进入这个房间时，他们会被随机传送到另一个房间。本报告将详细介绍这一功能的实现过程，包括设计决策、功能实现和用户交互。

## 设计与实现过程

### 1. 定义传送门房间


#### 方法

我们基于现有的普通房间类型扩展了一个新的房间类型——传送门房间。这种房间会在玩家进入时自动选择一个随机的目的地，并将玩家传送到该目的地。

### 2. 修改游戏逻辑以支持传送门房间


#### 方法

我们在游戏的主要逻辑中添加了对传送门房间的支持。每当玩家移动到一个新房间时，系统会检查这个房间是否为传送门房间。如果是，则立即触发传送逻辑，将玩家随机传送到另一个房间。同时，我们会更新玩家的房间历史记录，确保在传送后的新位置也被正确记录下来。

### 3. 处理传送后的房间历史


#### 方法

我们在处理玩家返回上一个房间的逻辑中加入了额外的检查。如果玩家尝试返回的房间是传送门房间，系统会阻止这次回退操作，并告知玩家不能返回到传送门房间。这样可以确保玩家不会因为误操作而陷入传送门房间的循环。

### 4. 用户反馈与提示信息


#### 方法

在玩家进入传送门房间并被传送到新房间时，系统会给出相应的提示信息，告诉玩家他们已经被传送到了哪个房间。此外，如果玩家尝试返回传送门房间，系统也会给出明确的提示信息，解释为什么不允许这样做。

### 5. 测试与验证


#### 方法

完成上述修改后，我们进行了全面的测试，涵盖了多种场景，包括正常移动、进入传送门房间、多次使用 `back` 命令等。通过这些测试，我们验证了所有功能都按预期工作，并根据测试结果进行了必要的调整和优化。


## 总结

本次功能扩展提升了游戏的趣味性和用户体验。通过定义传送门房间、修改游戏逻辑以支持传送门房间、处理传送后的房间历史、提供用户反馈与提示信息以及进行测试与验证，我们成功实现了玩家可以在游戏中使用传送门房间进行随机传送，并且在使用 `back` 命令时不会返回到传送门房间的功能。希望这份报告能帮助理解实现这一功能的过程。


# 游戏中的Player类设计与实现包括魔法饼干

## 摘要

本实验旨在游戏中创建一个独立的`Player`类，以模拟玩家在虚拟世界中的行为。该类不仅保存了玩家的基本信息和当前所在位置，还实现了对随身物品的管理，包括拾取、丢弃物品以及查看房间内和自身携带的物品。此外，还特别添加了一个魔法饼干（magic cookie）机制，它能够临时增加玩家的负重能力。

## 1. 玩家对象的设计

### 1.1 基本属性

- `姓名`：代表玩家的名字。
- `所在房间`：跟踪玩家当前位置。
- `携带物品`：列表形式存储玩家所携带的所有物品。
- `重量上限`：定义玩家可以携带的最大总重量。

### 1.2 功能需求

- **物品管理**：通过两个新命令“take”和“drop”，允许玩家从当前房间中拾取物品或将携带的物品丢弃回房间。
- **物品查询**：新增“items”命令，用于显示当前房间内的所有物件及其总重量，同时列出玩家携带的物品及总重量。
- **魔法饼干机制**：随机放置于特定房间内的魔法饼干可以通过“eat cookie”命令被玩家食用，从而提升其负重能力。

## 2. 新增命令的实现

### 2.1 “take”命令

为了实现“take”命令，系统首先需要检查玩家尝试拾取的物品是否存在于当前房间。如果存在，则进一步计算加入此物品后玩家携带物品的总重量是否会超过预设的重量上限。如果没有超出，系统会将物品从房间移除，并添加到玩家的携带物品列表中；否则，向玩家发出提示，告知他们无法拾起该物品，因为这会导致超重。

### 2.2 “drop”命令

“drop”命令的执行相对直接。当玩家选择丢弃某件或全部物品时，系统会将选定的物品从玩家的携带物品列表中移除，并将其添加到当前房间的物品列表中。这个过程更新了两个地方的数据——玩家的背包和房间内部的物品状态，确保两者保持同步。

### 2.3 “items”命令

“items”命令的功能是提供即时反馈给玩家。系统遍历当前房间内的所有物品，计算它们的总重量，并格式化输出这些信息。接着，系统同样处理玩家携带的物品，汇总并展示给玩家。这样，玩家就能清楚地知道周围环境和自己身上有什么东西，以及各自的重量情况。

### 2.4 “eat cookie”命令

对于“eat cookie”命令，系统首先确认玩家是否持有魔法饼干。如果有，那么在玩家选择使用这个命令后，系统会调整玩家的重量上限值，即增加一定的额外负重能力。这一变化可能是暂时性的，取决于游戏规则的设计。系统还会从玩家的携带物品中移除已使用的魔法饼干，保证逻辑的一致性。

## 3. 魔法饼干的引入

为了增加游戏趣味性，在某些房间中随机生成了魔法饼干。这些特殊物品不仅为游戏增添了探索元素，而且通过改变玩家的能力，丰富了游戏体验。






# 测试报告

## 概述

本报告涵盖了对游戏系统中三个关键功能模块的单元测试：玩家初始化、库存重量管理以及取物和丢弃命令。每个测试类都旨在验证相关功能是否按照预期工作，确保游戏逻辑的正确性。

---

## 玩家初始化测试 (`PlayerInitializationTest`)

### 描述

此测试集专注于验证玩家对象创建时的行为。它检查了玩家在创建时是否被正确地分配了一个名字和起始房间。测试包括两种情况：

- **带名字初始化**：当使用指定的名字创建玩家时，测试确认玩家的名字是否正确设置，并且玩家确实位于预期的起始房间。
- **默认名字初始化**：当没有提供玩家名字的情况下创建玩家时，测试确认玩家的名字为空字符串，并且同样位于正确的起始房间。

### 结论

通过这些测试，我们能够确保玩家对象在创建时总是处于一个已知且一致的状态，无论是否有提供自定义名字。

---

## 库存重量管理测试 (`InventoryWeightManagementTest`)

### 描述

这个测试集关注于玩家库存中物品的添加行为及其与承重限制的交互。测试覆盖了以下方面：

- **轻量级物品添加**：验证可以成功向玩家的库存中添加一个重量较轻的物品，并且该物品确实存在于玩家的库存中。
- **超重物品添加失败**：尝试添加一个超过玩家承重限制的物品，测试确认添加操作未能成功执行，物品也没有出现在玩家的库存里。
- **总重量计算准确性**：通过添加多个不同重量的物品到玩家的库存中，然后验证系统计算出的总重量是否准确无误。

### 结论

这些测试保证了玩家在携带物品时不会超出设定的承重限制，并且系统能够正确地计算玩家当前所携带的所有物品的总重量。

---

## 取物和丢弃命令测试 (`TakeDropCommandsTest`)

### 描述

最后一个测试集检验了玩家在游戏中执行“取物”和“丢弃”命令的功能。具体来说：

- **取物命令测试**：首先确保起始房间中存在一件名为 "key" 的物品，然后模拟玩家执行取物命令的过程，最终验证物品已被移除出房间并且出现在玩家的库存中。
- **丢弃命令测试**：先让玩家通过取物命令拾取 "key" 物品，之后模拟玩家执行丢弃命令的过程，最后验证物品已经返回到房间中，并且不再在玩家的库存里。

### 结论

通过这两个测试，我们可以确定玩家能够顺利地从房间中拿起物品并将其放入自己的库存中，同时也能将物品从库存中移除并放回房间内，这为玩家的游戏体验提供了重要的互动元素。

---

## 总结

上述三个测试集全面地验证了游戏系统中的核心功能，从玩家初始化到库存管理和物品交互命令的正确性。这些测试不仅确保了各功能模块按设计运行，而且也提高了整个系统的稳定性和用户体验质量。

## 结论

通过对`Player`类的功能扩展，我们成功地为游戏引入了一套完整的物品管理系统，增强了玩家与虚拟世界之间的互动。特别是魔法饼干机制，为玩家提供了额外的目标和奖励，进一步提升了游戏的吸引力。


