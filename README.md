# 代码结构及部件组成

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
## 维护代码

### 1. 使用命令模式（Command Pattern）

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

### 2. 其他缺陷及改进

#### 游戏状态管理

- **问题**
  - 游戏状态（如当前房间、是否退出等）分散在多个类中，不利于统一管理和调试。

- **改进**
  - 创建一个专门的游戏状态管理类，集中管理游戏的所有状态信息。

#### 异常处理

- **问题**
  - 代码中缺乏异常处理机制，可能会因为意外情况导致程序崩溃。

- **改进**
  - 增加适当的异常处理，确保程序在遇到错误时能够优雅地处理并给出提示信息。

#### 代码复用性

- **问题**
  - 某些功能（如显示所有有效命令）在不同类中有重复实现。

- **改进**
  - 将公共功能提取到一个工具类中，提高代码的复用性和可维护性。

### 实现细节

1. **游戏管理**
   - 创建游戏状态管理类，集中管理游戏状态。

2. **异常处理**
   - 增加异常处理机制，确保程序稳定性。
