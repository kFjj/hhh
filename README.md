# 1. 代码结构及部件组成

## 1. Command 类

**功能：** 表示用户输入的命令。

### 属性：
- `commandWord`：命令的第一个单词（通常是动词）。
- `secondWord`：命令的第二个单词（通常是名词或方向）。

### 方法：
- `Command(String firstWord, String secondWord)`：构造函数，初始化命令和第二个单词。
- `getCommandWord()`：获取命令词。
- `getSecondWord()`：获取第二个单词。
- `isUnknown()`：检查命令是否未知（即 `commandWord` 是否为 `null`）。
- `hasSecondWord()`：检查是否有第二个单词。

---

## 2. CommandWords 类

**功能：** 管理有效的命令列表。

### 属性：
- `validCommands`：一个字符串数组，包含所有有效的命令词。

### 方法：
- `isCommand(String aString)`：检查给定的字符串是否是有效命令。
- `showAll()`：打印所有有效的命令。

---

## 3. Game 类

**功能：** 游戏的主要逻辑，包括房间创建、命令处理等。

### 属性：
- `parser`：解析器对象，用于解析用户输入。
- `currentRoom`：当前玩家所在的房间。

### 方法：
- `Game()`：构造函数，初始化房间并设置解析器。
- `createRooms()`：创建所有房间并设置它们之间的连接。
- `play()`：游戏主循环，处理用户输入直到游戏结束。
- `printWelcome()`：打印欢迎信息。
- `processCommand(Command command)`：处理用户输入的命令。
- `printHelp()`：打印帮助信息。
- `goRoom(Command command)`：根据命令移动到指定方向的房间。
- `quit(Command command)`：处理退出命令。

---

## 4. Main 类

**功能：** 游戏的入口点。

### 方法：
- `main(String[] args)`：启动游戏实例并开始游戏。

---

## 5. Parser 类

**功能：** 解析用户输入的命令。

### 属性：
- `commands`：命令词管理对象。
- `reader`：用于读取用户输入的 `Scanner` 对象。

### 方法：
- `Parser()`：构造函数，初始化命令词管理对象和 `Scanner` 对象。
- `getCommand()`：从用户输入中解析命令。
- `showCommands()`：显示所有有效的命令。

---

## 6. Room 类

**功能：** 表示游戏中的房间。

### 属性：
- `description`：房间的描述。
- `exits`：一个 `HashMap`，存储房间的出口方向及其对应的房间。

### 方法：
- `Room(String description)`：构造函数，初始化房间描述和出口映射。
- `setExit(String direction, Room neighbor)`：设置房间的出口。
- `getShortDescription()`：获取房间的简短描述。
- `getLongDescription()`：获取房间的详细描述，包括出口信息。
- `getExit(String direction)`：获取指定方向的出口房间。
# 类图


![Class Diagram](/类图.jpeg）
# 2.维护代码

## 1. 使用命令模式（Command Pattern）

命令模式将请求封装成对象，从而可以使用不同的请求、队列或者日志来参数化其他对象。此外，命令模式还支持可撤销的操作。以下是改进后的代码实现步骤：

- **创建接口 `CommandExecutor`**
  - 方法：`void execute(Command command)`
    - 参数: `command`: 要执行的命令对象，包含命令单词和第二个单词（如果有的话）。
    - 功能: 执行给定的命令。具体的行为由实现该接口的类来决定。

- **为每个具体的命令创建一个执行器类**
  - 这些类实现了 `CommandExecutor` 接口，并提供了具体的执行逻辑。例如：
    - `GoCommandExecutor`
      - 描述：`GoCommandExecutor` 类实现了 `CommandExecutor` 接口，负责处理玩家输入的“前往某个方向”的命令（例如，“go north”）。该类通过检查命令的有效性并更新游戏状态来实现这一功能。
      - 主要成员：
        - 构造函数: `GoCommandExecutor(Game game)`
          - 参数: `game`: 游戏对象，包含了当前游戏的状态和逻辑。
          - 功能: 初始化 `GoCommandExecutor` 实例，并关联到特定的游戏对象。
        - 方法: `void execute(Command command)`
          - 参数: `command`: 前往某个方向的命令对象，包含命令单词和第二个单词（即方向）。
          - 功能: 执行前往某个方向的命令。具体步骤如下：
            1. 检查命令是否有第二个单词（即方向）。如果没有，则提示用户指定方向。
            2. 获取命令中的方向。
            3. 尝试从当前房间获取指定方向的下一个房间。
            4. 如果没有找到对应的房间，则提示用户“there is no door!”。
            5. 如果找到了对应的房间，则更新游戏的当前房间，并输出新房间的详细描述。

    - `HelpCommandExecutor`
      - 描述：`HelpCommandExecutor` 类实现了 `CommandExecutor` 接口，负责处理玩家输入的帮助命令（例如，“help”）。该类通过提供游戏的基本信息和可用命令列表来帮助玩家了解如何进行游戏。
      - 主要成员：
        - 构造函数: `HelpCommandExecutor(Parser parser)`
          - 参数: `parser`: 解析器对象，用于显示所有有效的命令。
          - 功能: 初始化 `HelpCommandExecutor` 实例，并关联到特定的解析器对象。
        - 方法: `void execute(Command command)`
          - 参数: `command`: 帮助命令对象。
          - 功能: 执行帮助命令。具体步骤如下：
            1. 输出一段描述性文本，告知玩家当前的情况。
            2. 调用解析器对象的 `showCommands` 方法，列出所有可用的命令。

    - `QuitCommandExecutor`
      - 描述：`QuitCommandExecutor` 类实现了 `CommandExecutor` 接口，负责处理玩家输入的退出命令（例如，“quit”）。该类通过检查命令的有效性并设置游戏的状态来决定是否退出游戏。
      - 主要成员：
        - 构造函数: `QuitCommandExecutor(Game game)`
          - 参数: `game`: 游戏对象，包含了当前游戏的状态和逻辑。
          - 功能: 初始化 `QuitCommandExecutor` 实例，并关联到特定的游戏对象。
        - 方法: `void execute(Command command)`
          - 参数: `command`: 退出命令对象。
          - 功能: 执行退出命令。具体步骤如下：
            1. 检查命令是否有第二个单词。如果有，则提示用户“Quit what?”，并将游戏的退出标志设置为 `false`。
            2. 如果没有第二个单词，则将游戏的退出标志设置为 `true`，表示玩家希望退出游戏。

- **在 `Game` 类中使用映射**
  - 关联命令字符串和对应的执行器实例，以避免大量的 `if-else` 语句。

## 2. 其他缺陷及改进

### 游戏状态管理

- **问题**
  - 游戏状态（如当前房间、是否退出等）分散在多个类中，不利于统一管理和调试。

- **改进**
  - 创建一个专门的游戏状态管理类，集中管理游戏的所有状态信息。

### 异常处理

- **问题**
  - 代码中缺乏异常处理机制，可能会因为意外情况导致程序崩溃。

- **改进**
  - 增加适当的异常处理，确保程序在遇到错误时能够优雅地处理并给出提示信息。

### 代码复用性

- **问题**
  - 某些功能（如显示所有有效命令）在不同类中有重复实现。

- **改进**
  - 将公共功能提取到一个工具类中，提高代码的复用性和可维护性。

### 实现细节

1. **游戏管理**
   - 创建游戏状态管理类，集中管理游戏状态。

2. **异常处理**
   - 增加异常处理机制，确保程序稳定性。
# 3.扩展功能I

扩展游戏，使得一个房间里可以存放任意数量的物件，每个物件可以有一个描述和一个重量值，玩家进入一个房间后，可以通过“look”命令查看当前房间的信息以及房间内的所有物品信息。

## 1. 创建 Item 类

描述：`Item` 类表示游戏中的一个物品。每个物品都有一个描述和重量，这些属性可以通过相应的 getter 方法获取。该类用于在游戏中管理物品的相关信息。

主要成员：
- 字段:
  - `description`: 物品的描述字符串。
  - `weight`: 物品的重量（单位为千克）。
- 构造函数:
  - `Item(String description, double weight)`
    - 参数:
      - `description`: 物品的描述。
      - `weight`: 物品的重量。
    - 功能: 初始化一个新的 `Item` 对象，并设置其描述和重量。
- 方法:
  - `getDescription()`
    - 返回值: 物品的描述字符串。
    - 功能: 返回物品的描述。
  - `getWeight()`
    - 返回值: 物品的重量（单位为千克）。
    - 功能: 返回物品的重量。
  - `getDetails()`
    - 返回值: 物品的详细信息字符串。
    - 功能: 返回包含物品描述和重量的详细信息字符串。

## 2. 修改 Room 类

添加新属性：`private List<Item> items;`

添加新方法：
- `public void addItem(Item item)`：添加物品到房间。
- `public void removeItem(Item item)`：移除房间中的物品。
- `public String getLongDescription()`：获取房间的详细描述，包括物品信息。

## 3. 扩展 CommandWords 类

添加新的命令词 `"look"`。

## 4. 创建 LookCommandExecutor 类

描述：`LookCommandExecutor` 类实现了 `CommandExecutor` 接口，负责处理玩家输入的“look”命令。该类通过输出当前房间的详细描述来帮助玩家了解周围的环境。

主要成员：
- 构造函数:
  - `LookCommandExecutor(Game game)`
    - 参数:
      - `game`: 游戏对象，包含了当前游戏的状态和逻辑。
    - 功能: 初始化 `LookCommandExecutor` 实例，并关联到特定的游戏对象。
- 方法:
  - `void execute(Command command)`
    - 参数:
      - `command`: “look”命令对象。
    - 功能: 执行“look”命令。具体步骤如下：
      1. 获取当前房间的详细描述。
      2. 输出当前房间的详细描述。
# 4 扩展功能II

在游戏中实现一个“back”命令，玩家输入该命令后会把玩家带回上一个房间。

## 1. 在 GameState 类中添加一个栈来存储玩家访问过的房间

- `private Stack<Room> roomHistory;`

## 2. 创建一个新的 BackCommandExecutor 类来处理“back”命令

描述：`BackCommandExecutor` 类实现了 `CommandExecutor` 接口，负责处理玩家输入的“back”命令。该类通过将玩家返回到上一个房间来支持游戏中的后退功能。

主要成员：
- 构造函数:
  - `BackCommandExecutor(Game game)`
    - 参数:
      - `game`: 游戏对象，包含了当前游戏的状态和逻辑。
    - 功能: 初始化 `BackCommandExecutor` 实例，并关联到特定的游戏对象。
- 方法:
  - `void execute(Command command)`
    - 参数:
      - `command`: “back”命令对象。
    - 功能: 执行“back”命令。具体步骤如下：
      1. 检查命令是否有第二个单词。如果有，则提示用户“use back”，并直接返回。
      2. 如果没有第二个单词，则调用 `game.goBack()` 方法将玩家返回到上一个房间。
      3. 输出返回后的房间的详细描述。

## 3. 在 CommandWords 类中添加新的命令词 "back"

- 添加新的命令词以支持“back”功能。

## 4. 在 Game 类中初始化 BackCommandExecutor 并将其添加到命令执行器映射中

- 在 `Game` 类的初始化过程中，创建 `BackCommandExecutor` 实例，并将其添加到命令执行器的映射中，以便能够处理玩家输入的“back”命令。
# 5 扩展功能III

在游戏中实现一个更高级的“back”命令，重复使用它就可以逐层回退几个房间，直到把玩家带回到游戏的起点。

## 1. 修改 GameState 类中的 `goBack` 方法

- 修改 `goBack` 方法的行为：
  - 当执行“back”命令时，当前房间不会加入到房间历史栈中。
  - 当玩家通过其他方式（如“go”命令）进入新房间时，会将当前房间加入房间历史栈中。

这样，玩家可以重复使用“back”命令，逐层回退经过的房间，直到回到游戏的起点。
# 6 拓展四：增加具有传输功能的房间

在游戏中增加具有传输功能的房间，每当玩家进入这个房间，就会被随机地传输到另一个房间。

## 1. 创建 `TeleportRoom` 类

`TeleportRoom` 类继承自 `Room` 类，表示游戏中的一个特殊房间——传送室。当玩家进入传送室时，会被随机传送到另一个房间。

**主要成员**：

- **构造函数**:
  - `TeleportRoom(String description, List<Room> rooms)`
    - **参数**:
      - `description`: 传送室的描述。
      - `rooms`: 包含所有可传送至的房间的列表。
    - **功能**: 初始化 `TeleportRoom` 实例，并关联到特定的游戏对象和房间列表。

- **属性**:
  - `Random random`
    - **类型**: `Random`
    - **功能**: 用于生成随机数，决定传送的目标房间。
  - `List<Room> rooms`
    - **类型**: `List<Room>`
    - **功能**: 存储所有可传送至的房间。

- **方法**:
  - `Room teleport()`
    - **返回值**: 随机选择的一个房间。
    - **功能**: 执行传送操作。具体步骤如下：
      1. 创建一个新的房间列表 `filteredRooms`，包含所有可传送至的房间，但排除当前传送室本身。
      2. 检查 `filteredRooms` 是否为空。如果为空，则输出提示信息并返回当前房间。
      3. 如果 `filteredRooms` 不为空，则从其中随机选择一个房间并返回。

## 2. 修改 `Game` 类

在 `Game` 类中也添加一个全部房间列表属性，在创建房间时，将所有房间加入到列表中。

- `private List<Room> rooms;` // 维护所有房间的列表
- 修改 `createRooms` 方法，将房间加入列表。
        
## 3. 在 `Game` 类中添加 `checkForTeleport` 方法

**方法名称**: `checkForTeleport`

**描述**:

`checkForTeleport` 方法用于检查当前房间是否是传送室（`TeleportRoom`）。如果是，则执行传送操作，将玩家随机传送到另一个房间，并输出相应的提示信息和新房间的描述。

**主要成员**：

- **方法**:
  - `private void checkForTeleport()`
    - **参数**: 无。
    - **功能**:
      1. 获取当前玩家所在的房间。
      2. 检查当前房间是否是 `TeleportRoom` 的实例。
      3. 如果是 `TeleportRoom`，则调用其 `teleport()` 方法获取下一个房间。
      4. 输出进入传送室和传送成功的提示信息。
      5. 更新玩家当前房间为传送后的房间。
      6. 输出传送后的新房间的详细描述。

## 4. 处理传送室的 `back` 问题

在 `GameState` 类中修改 `goBack` 方法，检测栈顶房间的类型是否是传送室。如果是，则提示不能执行 `back`，否则可以执行。
# 7扩展V
## 1. 创建 Player 类

### 描述
Player 类表示游戏中的一个玩家。该类包含了玩家的基本属性和操作方法，如姓名、当前位置、物品清单、房间历史记录等。此外，还提供了处理玩家移动、拾取和丢弃物品、管理携带重量等功能。

### 主要成员

#### 属性
- `name`: 玩家的姓名。
- `currentRoom`: 玩家当前所在的房间。
- `inventory`: 玩家持有的物品清单。
- `roomHistory`: 记录玩家访问过的房间历史。
- `wantToQuit`: 标记玩家是否想要退出游戏。
- `maxWeight`: 玩家最大可携带重量。
- `currentWeight`: 玩家当前携带的总重量。

#### 构造函数
- `Player(String name, Room currentRoom, double maxWeight)`
  - 参数:
    - `name`: 玩家的姓名。
    - `currentRoom`: 玩家初始所在房间。
    - `maxWeight`: 玩家的最大携带重量。
  - 功能: 初始化 Player 实例，并设置初始属性值。

#### 方法
- `getName()`
  - 返回值: 玩家的姓名。
  - 功能: 获取玩家的姓名。
- `setName(String name)`
  - 参数: 新的姓名。
  - 功能: 设置玩家的新姓名。
- `getCurrentRoom()`
  - 返回值: 当前房间。
  - 功能: 获取玩家当前所在的房间。
- `setCurrentRoom(Room currentRoom)`
  - 参数: 新的当前房间。
  - 功能: 设置玩家新的当前房间，并将之前的房间压入房间历史栈中（如果当前房间不是目标房间）。
- `getInventory()`
  - 返回值: 物品清单。
  - 功能: 获取玩家的物品清单。
- `addItem(Item item)`
  - 参数: 要添加的物品。
  - 功能: 将物品添加到玩家的物品清单中，并更新当前携带总重量。如果超过最大携带重量，则输出提示信息。
- `removeItem(Item item)`
  - 参数: 要移除的物品。
  - 功能: 从玩家的物品清单中移除物品，并更新当前携带总重量。如果物品不存在，则输出提示信息。
- `getRoomHistory()`
  - 返回值: 房间历史记录栈。
  - 功能: 获取玩家的房间历史记录栈。
- `goBack()`
  - 功能: 返回到上一个房间。如果玩家当前在传送室，则不允许返回。
- `getWantToQuit()`
  - 返回值: 游戏是否想要退出的状态。
  - 功能: 获取玩家是否想要退出游戏的状态。
- `setWantToQuit(boolean wantToQuit)`
  - 参数: 是否想要退出游戏。
  - 功能: 设置玩家是否想要退出游戏的状态。
- `getMaxWeight()`
  - 返回值: 最大携带重量。
  - 功能: 获取玩家的最大携带重量。
- `getCurrentWeight()`
  - 返回值: 当前携带总重量。
  - 功能: 获取玩家的当前携带总重量。
- `increaseMaxCarryWeight(double amount)`
  - 参数: 要增加的重量。
  - 功能: 增加玩家的最大携带重量。
- `setMaxCarryWeight(double maxCarryWeight)`
  - 参数: 玩家的新最大携带重量。
  - 功能: 设置玩家的新最大携带重量。

## 2. 实现 take 和 drop 命令

### TakeCommandExecutor

#### 描述
TakeCommandExecutor 类实现了 CommandExecutor 接口，用于处理游戏中的 take 命令。该类负责解析 take 命令，并执行相应的操作，即让玩家拾取房间中的物品。

#### 主要成员

- 属性:
  - `game`: 游戏对象，包含了当前游戏状态和相关方法。

- 构造函数:
  - `TakeCommandExecutor(Game game)`
    - 参数: 游戏对象。
    - 功能: 初始化 TakeCommandExecutor 实例，并关联到特定的游戏对象。

- 方法:
  - `execute(Command command)`
    - 参数: 需要执行的命令对象。
    - 功能: 解析并执行 take 命令。

### DropCommandExecutor

#### 描述
DropCommandExecutor 类实现了 CommandExecutor 接口，用于处理游戏中的 drop 命令。该类负责解析 drop 命令，并执行相应的操作，即让玩家丢弃物品到当前房间中。

#### 主要成员

- 属性:
  - `game`: 游戏对象，包含了当前游戏状态和相关方法。

- 构造函数:
  - `DropCommandExecutor(Game game)`
    - 参数: 游戏对象。
    - 功能: 初始化 DropCommandExecutor 实例，并关联到特定的游戏对象。

- 方法:
  - `execute(Command command)`
    - 参数: 需要执行的命令对象。
    - 功能: 解析并执行 drop 命令。
  - `findItemInInventory(String itemName)`
    - 参数: 物品描述。
    - 返回值: 找到的物品，如果没有找到则返回 null。
    - 功能: 在玩家的物品清单中查找指定的物品。

### 更新 Player 类以管理物品的总重量

- 方法1: `addItem(Item item)`
  - 描述: 将指定的物品添加到玩家的物品清单中。
  - 功能: 检查当前携带的总重量是否超过玩家的最大携带重量。如果不超过，则添加物品并更新总重量；否则，输出提示信息。

- 方法2: `removeItem(Item item)`
  - 描述: 从玩家的物品清单中移除指定的物品。
  - 功能: 检查玩家的物品清单是否包含要移除的物品。如果包含，则移除物品并更新总重量；否则，输出提示信息。

### 更新 Room 类以支持拾取和丢弃物品的操作

- 方法: `getItem(String description)`
  - 描述: 根据物品描述获取房间内的物品。
  - 返回值: 找到的物品对象，如果没有找到则返回 null。
  - 功能: 遍历房间内的所有物品列表，查找匹配的物品。

## 3. 实现 items 命令

### ItemsCommandExecutor

#### 描述
ItemsCommandExecutor 类实现了 CommandExecutor 接口，用于处理游戏中的 items 命令。该类负责解析 items 命令，并执行相应的操作，即显示当前房间内的物品及其总重量，以及玩家随身携带的物品及其总重量。

#### 主要成员

- 属性:
  - `game`: 游戏对象，包含了当前游戏状态和相关方法。

- 构造函数:
  - `ItemsCommandExecutor(Game game)`
    - 参数: 游戏对象。
    - 功能: 初始化 ItemsCommandExecutor 实例，并关联到特定的游戏对象。

- 方法:
  - `execute(Command command)`
    - 参数: 需要执行的命令对象。
    - 功能: 解析并执行 items 命令。
  - `calculateTotalWeight(List<Item> items)`
    - 参数: 物品列表。
    - 返回值: 物品列表的总重量。
    - 功能: 计算给定物品列表中所有物品的总重量。

### 更新 CommandWords 类

- 包含 items 命令。

### 在 Game 类中初始化

- 初始化 items 命令的执行器。

### 更新 Player 类

- 添加方法 `getInventory()`，用于获取玩家物品清单。

## 4. 实现魔法饼干及 eat cookie 命令

### EatCookieCommandExecutor

#### 描述
EatCookieCommandExecutor 类实现了 CommandExecutor 接口，用于处理游戏中的 eat cookie 命令。该类负责解析 eat cookie 命令，并执行相应的操作，即检查玩家是否拥有魔法饼干，如果有则让玩家食用并增加其最大携带重量。

#### 主要成员

- 属性:
  - `game`: 游戏对象，包含了当前游戏状态和相关方法。

- 构造函数:
  - `EatCookieCommandExecutor(Game game)`
    - 参数: 游戏对象。
    - 功能: 初始化 EatCookieCommandExecutor 实例，并关联到特定的游戏对象。

- 方法:
  - `execute(Command command)`
    - 参数: 需要执行的命令对象。
    - 功能: 解析并执行 eat cookie 命令。

### 更新 CommandWords 类

- 包含 eat cookie 命令。

### 在 Game 类中初始化

- 初始化 eat cookie 命令的执行器。

### 更新 Player 类

- 添加 `increaseMaxCarryWeight(double amount)`，用于增加玩家最大承重。
