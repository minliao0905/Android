



## 一、布局概述

布局定义应用中用户界面的结构

在Activity 中，布局中的所有元素都是使用和对象层次结构构建



![img](https://developer.android.google.cn/images/viewgroup_2x.png)

![image-20211212220643723](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212220643723.png)

1.编写xml

2.加载xml资源

3.属性

4.用户名

5.![image-20211212221146652](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221146652.png)

6.布局参数  layout_something

7.![image-20211212221409162](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221409162.png)

8.布局位置

![image-20211212221518500](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221518500.png)

9.大小，填充和边距![image-20211212221549152](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221549152.png)

10.通用布局

![image-20211212221643633](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221643633.png)

11.适配器构建布局**（常见布局为列表视图和网格视图）**

![image-20211212221708688](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212221708688.png)

处理点击事件

```java
 @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.ilv);
        //创建一个内部类实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Toast点击显示第"+position+"个item", Toast.LENGTH_SHORT).show();
            }
        });
    }

```



## 二、创建基于卡片的布局

[Card View]: https://developer.android.google.cn/reference/androidx/cardview/widget/CardView

![image-20211212223810716](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212223810716.png)

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    ... >
    <!-- A CardView that contains a TextView -->
    <androidx.cardview.widget.CardView
        xmlns:card_view="http://schemas.android.com/apk/res-auto"
        android:id="@+id/card_view"
        android:layout_gravity="center"
        android:layout_width="200dp"
        android:layout_height="200dp"
        card_view:cardCornerRadius="4dp">

        <TextView
            android:id="@+id/info_text"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </androidx.cardview.widget.CardView>
</LinearLayout>
```



![image-20211212222950347](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212222950347.png)

![image-20211212223501354](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212223501354.png)

## 三、自定义视图组件

### 1.自定义视图类

```java
class PieChart extends View {
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
```

1.定义自定义属性

![image-20211212224523343](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212224523343.png)

### 2.自定义绘图





## 四、Intent

![image-20211212225150123](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212225150123.png)

![image-20211212225210171](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212225210171.png)

### 1.意图结构

![image-20211212225514574](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212225514574.png)

### 2.意图解析

![image-20211212225631832](C:\Users\30997\AppData\Roaming\Typora\typora-user-images\image-20211212225631832.png)

**意图中有三条用于解析的信息：操作，类型和类别**

1.**操作：Action** 标准作业操作和标准广播操作

。`Context#startActivity``ACTION_MAIN``ACTION_EDIT`

- `ACTION_MAIN`
- `ACTION_VIEW`
- `ACTION_ATTACH_DATA`
- `ACTION_EDIT`
- `ACTION_PICK`
- `ACTION_CHOOSER`
- `ACTION_GET_CONTENT`
- `ACTION_DIAL`
- `ACTION_CALL`
- `ACTION_SEND`
- `ACTION_SENDTO`
- `ACTION_ANSWER`
- `ACTION_INSERT`
- `ACTION_DELETE`
- `ACTION_RUN`
- `ACTION_SYNC`
- `ACTION_PICK_ACTIVITY`
- `ACTION_SEARCH`
- `ACTION_WEB_SEARCH`
- `ACTION_FACTORY_TEST`

2**类别**

这些是当前的标准类别，可用于通过 进一步阐明意向。`addCategory(String)`

- `CATEGORY_DEFAULT`
- `CATEGORY_BROWSABLE`
- `CATEGORY_TAB`
- `CATEGORY_ALTERNATIVE`
- `CATEGORY_SELECTED_ALTERNATIVE`
- `CATEGORY_LAUNCHER`
- `CATEGORY_INFO`
- `CATEGORY_HOME`
- `CATEGORY_PREFERENCE`
- `CATEGORY_TEST`
- `CATEGORY_CAR_DOCK`
- `CATEGORY_DESK_DOCK`
- `CATEGORY_LE_DESK_DOCK`
- `CATEGORY_HE_DESK_DOCK`
- `CATEGORY_CAR_MODE`
- `CATEGORY_APP_MARKET`
- `CATEGORY_VR_HOME`

3**标准额外数据**

这些是当前的标准字段，可通过 用作额外数据。`putExtra(String, Bundle)`

- `EXTRA_ALARM_COUNT`
- `EXTRA_BCC`
- `EXTRA_CC`
- `EXTRA_CHANGED_COMPONENT_NAME`
- `EXTRA_DATA_REMOVED`
- `EXTRA_DOCK_STATE`
- `EXTRA_DOCK_STATE_HE_DESK`
- `EXTRA_DOCK_STATE_LE_DESK`
- `EXTRA_DOCK_STATE_CAR`
- `EXTRA_DOCK_STATE_DESK`
- `EXTRA_DOCK_STATE_UNDOCKED`
- `EXTRA_DONT_KILL_APP`
- `EXTRA_EMAIL`
- `EXTRA_INITIAL_INTENTS`
- `EXTRA_INTENT`

4**标志**

这些是可以在 Intent via 和 中使用的可能标志。有关所有可能标志的列表，请参见。`setFlags(int)``addFlags(int)``setFlags(int)`

## 五、设备兼容性

### 1. "兼容性"是什么意思？

当您阅读有关Android开发的更多信息时，您可能会在各种情况下遇到术语"兼容性"。有两种类型的兼容性：*设备兼容性*和*应用程序兼容性*。

由于Android是一个开源项目，因此任何硬件制造商都可以构建运行Android操作系统的设备。然而，只有当设备能够正确运行为Android*执行环境*编写的应用程序时，它才**是"Android兼容的"。**Android 执行环境的确切细节由[Android 兼容性程序](https://source.android.google.cn/compatibility/overview.html)定义，每个设备必须通过兼容性测试套件 （CTS） 才能被视为兼容。

作为应用程序开发人员，您无需担心设备是否与Android兼容，因为只有与Android兼容的设备才包括Google Play商店。因此，您可以放心，从Google Play商店安装您的应用程序的用户使用的是与Android兼容的设备。

但是，你确实需要考虑你的应用是否与每个潜在的设备配置**兼容**。由于 Android 可在各种设备配置上运行，因此某些功能并非在所有设备上都可用。例如，某些设备可能不包括指南针传感器。如果你的应用的核心功能需要使用罗盘传感器，则你的应用仅与包含罗盘传感器的设备兼容。

### 2.控制应用对设备的可用性

**从三个方面来看：设备特点，平台版本，屏幕配置**

#### 1设备特点

- **可根据需要配置需要的相关设备，在清单文件中声明**

 例如，如果你的应用在缺少罗盘传感器的设备上没有意义，则可以使用以下清单标记根据需要声明罗盘传感器：

```xml
<manifest ... >
    <uses-feature android:name="android.hardware.sensor.compass"
                  android:required="true" />
    ...
</manifest>
```

Google Play 商店会将您的应用所需的功能与每个用户设备上可用的功能进行比较，以确定您的应用是否与每台设备兼容。**如果设备未提供应用所需的所有功能，则用户无法安装你的应用。**

但是，如果应用的主要功能*不需要*设备功能，则应将[`必需`](https://developer.android.google.cn/guide/topics/manifest/uses-feature-element#required)的属性设置为并在运行时检查设备功能。**如果应用功能在当前设备上不可用，请正常降级相应的应用功能。(即不使用需要该功能的部分）**例如，您可以通过调用如下方式查询要素是否可用：`"false"``hasSystemFeature()`

```java
PackageManager pm = getPackageManager();
if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
    // This device does not have a compass, turn off the compass feature
    disableCompassFeature();
}
```

有关您可以使用的所有过滤器的信息，您可以通过 Google Play 商店控制您的应用对用户的可用性，请参阅[Google Play 上的过滤器](https://developer.android.google.cn/google/play/filters)文档。

#### 2平台版本

不同的设备可能会运行不同版本的 Android 平台，例如 Android 4.0 或 Android 4.4。每个后续平台版本通常会添加以前版本中不可用的新 API。为了指示哪组 API 可用，**每个平台版本都指定了 API[级别](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element#ApiLevels)。例如，Android 1.0 是 API 级别 1，Android 4.4 是 API 级别 19。**

API 级别允许你使用[`manifest`](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element)标记及其[`minSdkVersion`](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element#min)属性声明与应用兼容的最低版本。例如，[日历提供程序](https://developer.android.google.cn/guide/topics/providers/calendar-provider)API 是在 Android 4.0（API 级别 14）中添加的。**如果你的应用在没有这些 API 的情况下无法运行，则应将 API 级别 14 声明为应用支持的最低版本。**

[`minSdkVersion`](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element#min)属性声明与应用兼容的最低版本，[`而 targetSdkVersion`](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element#target)属性声明已优化应用的最高版本。

但是，请注意[`，`](https://developer.android.google.cn/guide/topics/manifest/uses-sdk-element)**元素中的属性将被[`build.gradle`](https://developer.android.google.cn/studio/build#build-files)文件中的相应属性覆盖**。因此，如果您使用的是 Android Studio，则必须在**build.gradle**此处指定 和 值：`minSdkVersion``targetSdkVersion`

```xml
android {
    defaultConfig {
        applicationId 'com.example.myapp'

        // Defines the minimum API level required to run the app.
        minSdkVersion 15

        // Specifies the API level used to test the app.
        targetSdkVersion 28

        ...
    }
}
```

#### 3.屏幕配置

Android可在各种尺寸的设备上运行，从手机到平板电脑和电视。为了按屏幕类型对设备进行分类，Android为每个设备定义了两个特征：**屏幕大小（屏幕的物理尺寸）**和**屏幕密度（屏幕上像素的物理密度，称为DPI）**。为了简化不同的配置，Android 将这些变体概括为多个组，使它们更易于定位：

- 四种广义大小：小、正常、大和大。
- 以及几种广义密度：mdpi（中），hdpi（高），xhdpi（超高），xxhdpi（超高）等。

### 3.如何支持不同的屏幕尺寸

如何采用以下技巧支持不同的屏幕尺寸：

- 使用允许布局调整大小的视图尺寸
- 根据屏幕配置创建备用界面布局
- 提供可以随视图一起拉伸的位图

但是，请注意，适应不同的屏幕尺寸并不一定会使您的应用与所有 Android 设备类型兼容。您应该采取其他措施[支持 Android Wear、Android TV、Android Auto 和 Chrome 操作系统设备](https://developer.android.google.cn/guide/practices/screens_support#devices)。

#### 1.创建灵活的布局

- **使用约束布局**

> 如需**创建适用于不同屏幕尺寸的自适应布局，最佳做法是将[`ConstraintLayout`](https://developer.android.google.cn/reference/androidx/constraintlayout/widget/ConstraintLayout)用作界面中的基本布局**。使用[`ConstraintLayout`](https://developer.android.google.cn/reference/androidx/constraintlayout/widget/ConstraintLayout)，您可以根据布局中视图之间的空间关系指定每个视图的位置和大小。通过这种方式，当屏幕尺寸改变时，所有视图都可以一起移动和拉伸。
>
> 如需使用[`ConstraintLayout`](https://developer.android.google.cn/reference/androidx/constraintlayout/widget/ConstraintLayout)构建布局，最简单的方法是使用 Android Studio 中的布局编辑器。借助该工具，您可以将新视图拖动到布局中，将其约束条件附加到父视图和其他同级视图以及修改视图的属性，完全不必手动修改任何 XML（请参见图 1）。

- **避免使用硬编码的布局尺寸**

- **创建备用布局**

- **创建可拉伸的九宫格位图** 如需根据位图创建九宫格图片方面的帮助，请参阅[创建可调整大小的位图](https://developer.android.google.cn/studio/write/draw9patch)。

- **针对所有屏幕尺寸进行测试**

> 务必针对各种屏幕尺寸测试您的应用，以便确保界面正确缩放。如果您无法访问具有各种不同屏幕尺寸的物理设备，则可以使用[Android 模拟器](https://developer.android.google.cn/studio/run/emulator)模拟任何屏幕尺寸。
>
> 如果您希望在物理设备上进行测试，但又不想购买设备，则可以使用[Firebase 测试实验室](https://firebase.google.cn/docs/test-lab/)访问 Google 数据中心内的设备。

- **声明特定的屏幕尺寸支持**

> 如果您不想让您的应用以特定的屏幕尺寸运行，您可以设置屏幕尺寸限制，甚至可以根据设备的屏幕配置限制哪些设备可以安装您的应用。如需了解详情，请参阅[声明受限屏幕支持](https://developer.android.google.cn/guide/practices/screens-distribution)。





## 六、使用约束布局构建响应ui

#### 1.约束概述

要定义视图在 中的位置，**必须为视图添加至少一个水平约束和一个垂直约束。**

每个约束都表示与另一个视图、父布局或不可见的准则的连接或对齐方式。每个约束定义视图沿垂直或水平轴的位置。因此，每个视图必须为每个轴至少具有一个约束，但通常需要更多约束。`ConstraintLayout`

**准备工作**

要将约束布局添加到项目中，确定在build.grade中进行了相关操作

> 1. 确保在顶级文件中声明了存储库：
> 2. 将库作为依赖项添加到模块级文件中，如下面的示例所示。请注意，最新版本可能与示例中显示的版本不同：
> 3. 在工具栏或同步通知中，单击将项目与 Gradle 文件同步。

**创建约束时，请记住以下规则**：

> - 每个视图必须至少有两个约束：一个水平和一个垂直。
> - 只能在约束手柄和共享同一平面的锚点之间创建约束。因此，视图的垂直平面（左侧和右侧）只能限制到另一个垂直平面;并且基线只能限制为其他基线。
> - 每个约束句柄只能用于一个约束，但您可以创建多个约束（从不同的视图）到同一锚点。
>
> 您可以通过执行以下任一操作来删除约束：
>
> - 单击某个约束以将其选中，然后按 。`Delete`
> - 长按 （在 macOS 上），然后单击约束锚点。请注意，约束将变为红色，表示您可以单击将其删除，

#### 2.可以使用约束来实现不同类型的布局行为，如以下各节所述。

**家长职位**

将视图的一侧约束到布局的相应边缘。

在图 7 中，视图的左侧连接到父布局的左边缘。可以使用边距定义与边的距离。



![img](https://developer.android.google.cn/training/constraint-layout/images/parent-constraint_2x.png)

**图 7.**对父级的水平约束

**订单位置**

定义两个视图的垂直或水平显示顺序。

在图 8 中，B 被约束为始终位于 A 的右侧，而 C 被限制在 A 的下方。但是，这些约束并不意味着对齐，因此 B 仍然可以上下移动。

![img](https://developer.android.google.cn/training/constraint-layout/images/position-constraint_2x.png)

**图 8.**水平和垂直约束

**对准**

将视图的边缘与另一个视图的同一边缘对齐。

在图 9 中，B 的左侧与 A 的左侧对齐。如果要对齐视图中心，请在两侧创建约束。

可以通过将视图从约束向内拖动来偏移对齐方式。例如，图 10 显示了具有 24dp 偏移对齐方式的 B。偏移量由受约束视图的边距定义。

还可以选择要对齐的所有视图，然后单击工具栏中的"**对齐**![img](https://developer.android.google.cn/studio/images/buttons/layout-editor-align.png)"以选择对齐类型。

![img](https://developer.android.google.cn/training/constraint-layout/images/alignment-constraint_2x.png)

**图 9.**水平对齐约束

![img](https://developer.android.google.cn/training/constraint-layout/images/alignment-constraint-offset_2x.png)

**图 10.**偏移水平对齐约束

**基线对齐**

将一个视图的文本基线与另一个视图的文本基线对齐。

在图 11 中，B 的第一行与 A 中的文本对齐。

若要创建基线约束，请右键单击要约束的文本视图，然后单击"**显示基线"。**然后单击文本基线并将该行拖动到另一个基线。

![img](https://developer.android.google.cn/training/constraint-layout/images/baseline-constraint_2x.png)

**图 11.**基线对齐约束

**约束准则**

您可以添加垂直或水平指南，您可以将视图限制到该指南，并且该指南对应用程序用户不可见。您可以根据相对于布局边缘的 dp 单位或百分比在布局中定位参考线。

要创建指南，请单击工具栏中的"**指南**![img](https://developer.android.google.cn/studio/images/buttons/layout-editor-guidelines.png)"，然后单击"**添加垂直指南**"或**"添加水平指南"。**

拖动虚线以重新定位它，然后单击参考线边缘的圆圈以切换测量模式。

![img](https://developer.android.google.cn/training/constraint-layout/images/guideline-constraint_2x.png)

**图 12.**受制于准则的视图

**约束到障碍**

与指南类似，障碍是一条不可见的线，您可以将视图限制到该线。除了障碍不能确定自己的位置;相反，障碍位置会根据其中包含的视图的位置移动。如果要将视图限制为一组视图而不是一个特定视图，这将非常有用。

例如，图 13 显示视图 C 被限制在障碍的右侧。障碍设置为视图 A 和视图 B 的"结束"（或从左到右布局中的右侧）。因此，障碍的移动取决于视图 A 或视图 B 的右侧是最右边。

若要创建障碍，请按照下列步骤操作：

1. 单击工具栏中的"**准则**![img](https://developer.android.google.cn/studio/images/buttons/layout-editor-guidelines.png)"，然后单击"**添加垂直障碍"**或**"添加水平障碍"。**
2. 在"**组件树**"窗口中，选择障碍内所需的视图，然后将它们拖动到障碍组件中。
3. 从组件**树**中选择障碍，打开**"属性**![img](https://developer.android.google.cn/studio/images/buttons/window-properties.png)"窗口，然后设置**障碍Direction**。

#### 3.具体布局使用：

现在，您可以创建从另一个视图到障碍的约束。

您还可以将障碍*内的*视图限制为障碍。这样，您可以确保屏障中的所有视图始终彼此对齐，即使您不知道哪个视图最长或最高。

您还可以在障碍物内包含一个准则，以确保障碍物的"最小"位置。

ConstraintLayout中，居中的操作就是通过边缘约束实现的，如果一个View同时有以下属性,那就表示在父布局垂直方向居中

```
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
```

1
2
所以在父布局水平方向居中你一定也能想到了

```
app:layout_constraintLeft_toLeftOf="parent"
app:layout_constraintRight_toRightOf="parent"
```



## 七、搜索

**searchview**

### 1.基础知识

**两种实现方法 ：搜索对话框，搜索微件**

- **搜索对话框**是由 Android 系统控制的 UI 组件。当用户激活时，搜索对话框将显示在活动的顶部，如图 1 所示。

  Android 系统控制搜索对话框中的所有事件。当用户提交查询时，系统会将查询传递到您指定用于处理搜索的活动。该对话框还可以在用户键入时提供搜索建议。

- **搜索微件**是可以放置在布局中任何位置的实例。默认情况下，搜索小组件的行为类似于标准小组件，不执行任何操作，但您可以对其进行配置，以便 Android 系统处理所有输入事件，向相应活动提供查询，并提供搜索建议（就像搜索对话框一样）

当您准备好向应用程序添加搜索功能时，Android 会通过显示在活动窗口顶部的**搜索对话框**或可在布局中插入的搜索小组件来帮助您实现用户界面。**搜索对话框和小组件都可以将用户的搜索查询传递到应用程序中的特定活动。**这样，用户可以从搜索对话框或小组件可用的任何活动启动搜索，并且系统启动相应的活动以执行搜索并显示结果。

可用于搜索对话框和微件的其他功能包括：

- 语音搜索
- 基于最近查询的搜索建议
- **与应用程序数据中的实际结果匹配的搜索建议**

相关资源：

- 可搜索的配置search.xml

  一个 XML 文件，用于为搜索对话框或微件配置某些设置。它包括语音搜索、搜索建议和搜索框提示文本等功能的设置。

- 可搜索的活动 `Activity`

  接收搜索查询、搜索数据并显示搜索结果的 

- 搜索界面，由以下任一方提供：

  - 搜索对话框

    默认情况下，搜索对话框处于隐藏状态，但在您呼叫时（当用户按下"搜索"按钮时）显示在屏幕顶部。`onSearchRequested()`

  - 或者，一个小部件

    ```
    SearchView
    ```

    使用搜索微件，您可以将搜索框放在活动中的任意位置。通常不应将其放在活动布局中，而应将其用作应用栏中的操作视图。`SearchView`

### 2.创建可搜索的配置

本文档的其余部分将向您展示如何创建可搜索配置、可搜索活动，以及如何使用搜索对话框或搜索微件实现搜索界面。

首先需要的是一个称为可搜索配置的 XML 文件。它配置搜索对话框或构件的某些 UI 方面，并定义建议和语音搜索等功能的行为方式。此文件传统上是命名的，必须保存在项目目录中。`searchable.xml``res/xml/`

**注意：**系统使用此文件来实例化对象，但您无法在运行时自行创建此对象，而必须在 XML 中声明可搜索的配置。`SearchableInfo`

可搜索的配置文件必须包含[`<可搜索>`](https://developer.android.google.cn/guide/topics/search/searchable-config#searchable-element)元素作为根节点，并指定一个或多个属性。例如：

```xml
<?xml version="1.0" encoding="utf-8"?>
<searchable xmlns:android="http://schemas.android.com/apk/res/android"
    android:label="@string/app_label"
    android:hint="@string/search_hint" >
</searchable>
```

### 3.创建可搜索的活动

####  声明可搜索的活动

```
<application ... >
    <activity android:name=".SearchableActivity" >
        <intent-filter>
            <action android:name="android.intent.action.SEARCH" />
        </intent-filter>
        <meta-data android:name="android.app.searchable"
                   android:resource="@xml/searchable"/>
    </activity>
    ...
</application>
```

#### 执行搜索

- 接受查询

- ```java
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.search);
  
      // Get the intent, verify the action and get the query
      Intent intent = getIntent();
      if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        doMySearch(query);
      }
  }
  ```

- 搜索数据

  存储和搜索数据是应该根据需求和数据格式仔细考虑

  > 如果数据是在线存储的，则用户的数据连接可能会抑制感知到的搜索性能。您可能希望显示旋转的进度轮，直到搜索返回。有关如何显示进度轮的信息，请参阅网络 API 的参考和[创建进度对话框](https://developer.android.google.cn/guide/topics/ui/dialogs#ProgressDialog)。`android.net`

- 展示结果

  无论您的数据位于何处以及如何搜索数据，我们都建议您使用 .这样，您可以轻松地将所有搜索结果呈现在 .如果数据来自 SQLite 数据库查询，则可以将结果应用于 使用 .如果数据采用某种其他类型的格式，则可以创建 的扩展名。`Adapter``ListView``ListView``CursorAdapter``BaseAdapter`

### 4.使用搜索对话框

```xml
<application ... >
    <!-- this is the searchable activity; it performs searches -->
    <activity android:name=".SearchableActivity" >
        <intent-filter>
            <action android:name="android.intent.action.SEARCH" />
        </intent-filter>
        <meta-data android:name="android.app.searchable"
                   android:resource="@xml/searchable"/>
    </activity>

    <!-- this activity enables the search dialog to initiate searches
         in the SearchableActivity -->
    <activity android:name=".OtherActivity" ... >
        <!-- enable the search dialog to send searches to SearchableActivity -->
        <meta-data android:name="android.app.default_searchable"
                   android:value=".SearchableActivity" />
    </activity>
    ...
</application>
```

例如，您应该在["选项菜单"或](https://developer.android.google.cn/guide/topics/ui/menus#options-menu)UI 布局中添加一个调用 的"搜索"按钮。为了与Android系统和其他应用程序保持一致，您应该使用[操作栏图标包](https://developer.android.google.cn/design/downloads#action-bar-icon-pack)中提供的Android搜索图标来标记按钮。`onSearchRequested()`

### 5.使用“搜索”微件searchView

### 配置搜索微件

创建[可搜索配置](https://developer.android.google.cn/guide/topics/search/search-dialog?hl=en#SearchableConfiguration)和[可搜索活动](https://developer.android.google.cn/guide/topics/search/search-dialog?hl=en#SearchableActivity)后，如上所述，需要为每个 配置和可搜索活动启用辅助搜索。为此，可以调用并表示可搜索配置的对象。`SearchView``setSearchableInfo()``SearchableInfo`

您可以通过调用 来获取对 的引用。`SearchableInfo``getSearchableInfo()``SearchManager`

例如，如果您在[应用栏](https://developer.android.google.cn/training/appbar)中使用 a 作为操作视图，则应在回调期间启用小组件：`SearchView``onCreateOptionsMenu()`

```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the options menu from XML
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.options_menu, menu);

    // Get the SearchView and set the searchable configuration
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
    // Assumes current activity is the searchable activity
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

    return true;
}
```

### 6.添加语音搜索

### 7.添加搜索建议









## 八、Animation

### 1、分类：

第一：首先我们要知道在android中基础动画中主要分为四种：

- 1：Tween Animation.变换动画
- 2：Layout Animation 布局动画
- 3：Frame Animation.帧动画
- 4：Property Animation 属性动画

在本篇中先学习前两种方法。

公用功能：

![image-20211214130453071](D:\Typoramarkdown学习编辑\img\image-20211214130453071.png)

#### 缩放动画：ScaleAnimation

创建步骤：

- 创建动画对象

- 设置

- 启动动画

  ```java
  ScaleAnimation animation = new ScaleAnimation(高度，宽度，缩放的圆心，延迟时间，持续时间，等参数);
  iv_animation.startAnimation(animation)
      
  //    定义方法
      public void startCode（*）animation（）{
      在这里创建和设置，调用启动
  }
  ```

xml实现动画

- 创建动画xml

  ```xml
  <scale>
  </scale>
  ```

#### 旋转动画：RotateAnimation



### 2组件应用

#### 进度条ProgressBar

ProgressBar继承与View类，直接子类有AbsSeekBar和ContentLoadingProgressBar， 其中AbsSeekBar的子类有SeekBar和RatingBar，可见这二者也是基于ProgressBar实现的

##### 1**常用属性详解：**

> - android:**max**：进度条的最大值
> - android:**progress**：进度条已完成进度值
> - android:**progressDrawable**：设置轨道对应的Drawable对象
> - android:**indeterminate**：如果设置成true，则进度条不精确显示进度
> - android:**indeterminateDrawable**：设置不显示进度的进度条的Drawable对象
> - android:**indeterminateDuration**：设置不精确显示进度的持续时间
> - android:**secondaryProgress**：二级进度条，类似于视频播放的一条是当前播放进度，一条是缓冲进度，前者通过progress属性进行设置！

对应的再Java中我们可调用下述方法：

> - **getMax**()：返回这个进度条的范围的上限
> - **getProgress**()：返回进度
> - **getSecondaryProgress**()：返回次要进度
> - **incrementProgressBy**(int diff)：指定增加的进度
> - **isIndeterminate**()：指示进度条是否在不确定模式下
> - **setIndeterminate**(boolean indeterminate)：设置不确定模式下

##### 2自定义进度条

- 定义一个类继承ProgressBar

- 在该类中定义ondraw方法，调用Canvs API 

  >. getWidth() / getHeight（）返回值
  >
  >返回的值是 **View在Layout过程中的宽 / 高，即最终的宽 / 高**

![image-20211215165412952](D:\Typoramarkdown学习编辑\img\image-20211215165412952.png)

![image-20211215165445562](D:\Typoramarkdown学习编辑\img\image-20211215165445562.png)

使用原来的progressbar 

```xml
    <ProgressBar
        android:id="@+id/progress_bar"
        style="@android:style/Widget.ProgressBar.Horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:max="1000"
        android:progress="0"
        android:layout_marginBottom="10dp"/>
```













## 九、内存

### 1.static关键字

对于static的理解：

- 静态变量：（类变量） 

  - 在运行时，java虚拟机只为该静态变量分配一次内存，在加载类的过程完成静态变量的内存分配

  - 在类的内部，可以在任何方法内直接访问静态变量。
  - 在其他类中，可以通过类名访问该类中的静态变量。若通过对象访问，则访问无效，访问变量的值不会改变

- 实例变量

  - 每创建一个实例，Java 虚拟机就会为实例变量分配一次内存。
  - 在类的内部，可以在非静态方法中直接访问实例变量。
  - 在本类的静态方法或其他类中则需要通过类的实例对象进行访问。

- 静态方法

  静态方法与实例方法的区别如下：

  - 静态方法不需要通过它所属的类的任何实例就可以被调用，因此在静态方法中不能使用 this 关键字，也不能直接访问所属类的实例变量和实例方法，但是可以直接访问所属类的静态变量和静态方法。另外，和 this 关键字一样，super 关键字也与类的特定实例相关，所以在静态方法中也不能使用 super 关键字。

   在实例方法中可以直接访问所属类的静态变量、静态方法、实例变量和实例方法。

- 静态代码块

  静态代码块指 Java 类中的 static{ } 代码块，主要用于初始化类，为类的静态变量赋初始值，提升程序性能。

  静态代码块的特点如下：

  - 静态代码块类似于一个方法，但它不可以存在于任何方法体中。
  - 静态代码块可以置于类中的任何地方，类中可以有多个静态初始化块。 
  - Java 虚拟机在加载类时执行静态代码块，所以很多时候会将一些只需要进行一次的初始化操作都放在 static 代码块中进行。
  - 如果类中包含多个静态代码块，则 Java 虚拟机将按它们在类中出现的顺序依次执行它们，每个静态代码块只会被执行一次。
  - 静态代码块与静态方法一样，不能直接访问类的实例变量和实例方法，而需要通过类的实例对象来访问。

  

  #### 总结：
  
  若要在其他类中，使用get（） 或者 set（） 方法，改变实体类中的静态变量，那get() 方法也需要设置为static 
  
  最好不要设置为静态变量，避免访问无效。

### 2.java内存管理

内存分配由程序完成，对象的释放由垃圾回收机制决定和执行

#### 1.jvm内存区域组成

![img](https://images0.cnblogs.com/i/288799/201405/281726404166686.jpg)

1**栈内存**：（优点：存取速度比堆要快）在**函数**中定义的**基本类型变量和对象的引用变量**都在函数的栈内存中分配,**栈中的数据可共享**；

2**堆内存**：（优点：在运行时动态分配内存）用来存放**由new创建的对象和数组以及对象的实例变量** 在函数（代码块）中定义一个变量时，java就在栈中为这个变量分配内存空间，**当超过变量的作用域后，java会自动释放掉为该变量所分配的内存空间；在堆中分配的内存由java虚拟机的自动垃圾回收器来管理**



#### 2 Java中数据在内存中是如何存储的

a) 基本数据类型

  Java的基本数据类型**共有8种，即int, short, long, byte, float, double, boolean, char(注意，并没有string的基本类型)。存在于栈中。**
另外，栈有一个很重要的特殊性，就是存在栈中的数据可以共享。比

b)  对象

在Java中，创建一个对象包括对象的声明和实例化两步
(1)声明对象时的内存模型
　**用Rectangle rect**；**声明一个对象rect**时，将在栈内存为对象的引用变量rect**分配内存空间**，**但Rectangle的值为空**，称rect是一个空对象。空对象不能使用，因为它还**没有引用任何"实体**"。
(2)对象实例化时的内存模型
　当执行rect=new Rectangle(3,5)；时，会做两件事：　在堆内存中为类的成员变量width,height**分配内存**，并将其初始化为各数据类型的默认值；接着进行**显式初始化**（类定义时的初始化值）；最后调用构造方法，为**成员变量赋值**。 **返回堆内存中对象的引用（**相当于首地址）给引用变量rect,以后就可以通过rect来引用堆内存中的对象了。

c)  创建多个不同的对象实例
Rectangle r1= new Rectangle(3,5);
Rectangle r2= new Rectangle(4,6);

这样相当于创建了两个对象实例

或者：
Rectangle r1= new Rectangle(3,5);
Rectangle r2=r1;
这样相当于在堆内存中只创建了一个对象实例，在栈内存中创建了两个对象引用，两个对象引用同时指向一个对象实例。

d)  包装类

​     **基本型别都有对应的包装类**：如int对应Integer类，double对应Double类等，**基本类型的定义都是直接在栈中**

如果用包装类来创建对象， **Integer i（i此时是对象） = new Integer(5)**；这样**，i对象数据存储在堆中**，**i的引用存储在栈中**，通过栈中的引用来操作对象。


e)  String

 **String是一个特殊的包装类数据**。

两种方式创建：

String str = new String("abc")；String str = "abc";
第一种创建方式，和普通对象的的创建过程一样；

第二种创建方式，Java内部将此语句转化为以下几个步骤：
(1) 先定义一个名为str的对String类的对象引用变量：String str；
(2) 在栈中查找有没有存放值为"abc"的地址，如果没有，则开辟一个存放字面值为"abc"
地址，接着创建一个新的String类的对象o，并将o的字符串值指向这个地址，而且在栈
这个地址旁边记下这个引用的对象o。如果已经有了值为"abc"的地址，则查找对象o，并返回o的地址。
(3) 将str指向对象o的地址。
值得注意的是，一般String类中字符串值都是直接存值的。但像String str = "abc"；这种
合下，其字符串值却是保存了一个指向存在栈中数据的引用。
为了更好地说明这个问题，我们可以通过以下的几个代码进行验证。
String str1="abc"；
String str2="abc"；
System.out.println(s1==s2)；//true。表示指向同一个对象
我们再接着看以下的代码。
String str1= new String("abc")；一个在栈中一个在堆中
String str2="abc"；
System.out.println(str1==str2)；//false
创建了两个引用。创建了两个对象。两个引用分别指向不同的两个对象。　　  以上两段代码说明，只要是用new()来新建对象的，都会在堆中创建，而且其字符串是单独存值的，即使与栈中的数据相同，也不会与栈中的数据共享。

f)  数组

​     当定义一个数组，int x[]；或int []x；时，在栈内存中创建一个数组引用，通过该引用（即数组名）来引用数组。x=new int[3]；将在堆内存中分配3个保存int型数据的空间，堆内存的首地址放到栈内存中，每个数组元素被初始化为0。


g)  静态变量

​     **用static的修饰的变量和方法**，实际上是**指定了变量和方法在内存中的"固定位置"－static storage**，

可以理解为所有实例对象共有的内存空间。static变量有点类似于C中的全局变量的概念；静态表示的是内存的共享，就是它的每一个实例都指向同一个内存地址。把static拿来，就是告诉JVM它是静态的，它的引用（含间接引用）都是指向同一个位置，在那个地方，你把它改了，它就不会变成原样，你把它清理了，它就不会回来了。     那静态变量与方法是在什么时候初始化的呢？对于两种不同的类属性，static属性与instance属性，初始化的时机是不同的。instance属性在创建实例的时候初始化，**static属性在类加载**，**也就是第一次用到这个类的时候初始化，对于后来的实例的创建，不再次进行初始化。**     



关于实例变量，局部变量和对象的这三种变量

**实例变量和对象驻留在堆上**

**局部变量在栈上**



#### 3.java垃圾回收机制

**垃圾回收是一种动态存储管理技术，它自动地释放不再被程序引用的对象**，按照特定的垃圾收集算法来实现资源自动回收的功能。当一个对象不再被引用的时候，内存回收它占领的空间，以便空间被后来的新对象使用，**以免造成内存泄露**

**满足条件即可回收**：

- 空引用：当对象没有对他可到达引用时，他就符合垃圾回收的条件。也就是说如果没有对他的引用，删除对象的引用就可以达到目的，因此我们可以把引用变量设置为null，来符合垃圾回收的条件。
  StringBuffer sb = new StringBuffer("hello");
  System.out.println(sb);
  sb=null;
-  重新为引用变量赋值：可以通过设置引用变量引用另一个对象来解除该引用变量与一个对象间的引用关系。
  StringBuffer sb1 = new StringBuffer("hello");
  StringBuffer sb2 = new StringBuffer("goodbye");
  System.out.println(sb1);
  sb1=sb2;//此时"hello"符合回收条件 
-  方法内创建的对象：所创建的局部变量仅在该方法的作用期间内存在。一旦该方法返回，在这个方法内创建的对象就符合垃圾收集条件。有一种明显的例外情况，就是方法的返回对象。
  public static void main(String[] args) {
  Date d = getDate();
  System.out.println("d = " + d);
  }
  private static Date getDate() {
  Date d2 = new Date();
  StringBuffer now = new StringBuffer(d2.toString());
  System.out.println(now);
  return d2;
  }
-  隔离引用：这种情况中，被回收的对象仍具有引用，这种情况称作隔离岛。若存在这两个实例，他们互相引用，并且这两个对象的所有其他引用都删除，其他任何线程无法访问这两个对象中的任意一个。也可以符合垃圾回收条件。

#### 4.final问题

  final使得被修饰的变量"不变"

```java
final StringBuffer a=new StringBuffer("immutable");
final StringBuffer b=new StringBuffer("not immutable");
a=b;//编译期错误
final StringBuffer a=new StringBuffer("immutable");
final StringBuffer b=new StringBuffer("not immutable");
a=b;//编译期错误 
引用指向的对象不变：
final StringBuffer a=new StringBuffer("immutable");
a.append(" broken!"); //编译通过
final StringBuffer a=new StringBuffer("immutable");
a.append(" broken!"); //编译通过 
```

**final只对引用的"值"(也即它所指向的那个对象的内存地址)有效，它迫使引用只能指向初始指向的那个对象，改变它的指向会导致编译期错误**。至于它所指向的对象的变化，final是不负责的。这很类似==操作符：==操作符只负责引用的"值"相等，至于这个地址所指向的对象内容是否相等

**但是对于用final修饰的类变量，虚拟机不会为其赋予初值，必须在constructor (构造器)结束之前被赋予一个明确的值。可以修改为"final int i = 0;"。**



### 3.编写程序时需要注意的小细节

1. 尽早释放无用对象的引用

   **好的办法是使用临时变量的时候，让引用变量在退出活动域后，自动设置为null，**暗示垃圾收集器来收集该对象，防止发生内存泄露。对于仍然有指针指向的实例，jvm就不会回收该资源,因为垃圾回收会将值为null的对象作为垃圾，提高GC回收机制效率；

2. 、**定义字符串应该尽量使用 String str="hello"; 的形式 ，避免使用String str = new String("hello"); 的形式**。因为要使用内容相同的字符串，不必每次都new一个String。例如我们要在构造器中对一个名叫s的String引用变量进行初始化，把它设置为初始值，应当这样做： 

   ```java
    public class Demo {
      private String s;
      public Demo() {
      s = "Initial Value";
      }
   }
    而非
   s = new String("Initial Value"); 
   s = new String("Initial Value"); 
   ```

      后者每次都会调用构造器，生成新对象，性能低下且内存开销大，并且没有意义，因为String对象不可改变，所以对于内容相同的字符串，只要一个String对象来表示就可以了。也就说，多次调用上面的构造器创建多个对象，他们的String类型属性s都指向同一个对象。  

3、我们的程序里不可避免大量使用字符串处理，避免使用String，应大量使用StringBuffer ，因为**String被设计成不可变(immutable)**类，所以它的所有对象都是不可变对象，若对原来的对象进行修改，该对象会指向其他对象，而原来的string对象依然存在。

请看下列代码；
String s = "Hello";  
s = s + " world!"; 
String s = "Hello";
s = s + " world!";
    在这段代码中，**s原先指向一个String对象，内容是 "Hello"，然后我们对s进行了+操作，那么s所指向的那个对象是否发生了改变呢？答案是没有。这时，s不指向原来那个对象了，而指向了另一个 String对象，内容为"Hello world!"，原来那个对象还存在于内存之中，只是s这个引用变量不再指向它了。**     通过上面的说明，我们很容易导出另一个结论，如果经常对字符串进行各种各样的修改，或者说，不可预见的修改，那么使用String来代表字符串的话会引起很大的内存开销。因为 String对象建立之后不能再改变，所以对于每一个不同的字符串，都需要一个String对象来表示。这时，**应该考虑使用StringBuffer类，它允许修改**，而不是每个不同的字符串都要生成一个新的对象。并且，这两种类的对象转换十分容易。
   4、**尽量少用静态变量 ，因为静态变量是全局的，GC不会回收的**；
   5、**尽量避免在类的构造函数里创建、初始化大量的对象 ，防止在调用其自身类的构造器时造成不必要的内存资源浪费**，尤其是大对象，JVM会突然需要大量内存，这时必然会触发GC优化系统内存环境；显示的声明数组空间，而且申请数量还极大。     以下是初始化不同类型的对象需要消耗的时间：

| 运算操作 | 示例         | 标准化时间 |
| -------- | ------------ | ---------- |
| 本地赋值 | i = n        | 1.0        |
| 实例赋值 | this.i = n   | 1.2        |
| 方法调用 | Funct()      | 5.9        |
| 新建对象 | New Object() | 980        |
| 新建数组 | New int[10]  | 3100       |

从表1可以看出，新建一个对象需要980个单位的时间，是本地赋值时间的980倍，是方法调用时间的166倍，而新建一个数组所花费的时间就更多了。
   6、**尽量在合适的场景下使用对象池技术 以提高系统性能，缩减缩减开销，**但是要注意对象池的尺寸不宜过大，及时清除无效对象释放内存资源，综合考虑应用运行环境的内存资源限制，避免过高估计运行环境所提供内存资源的数量。
   7、大集合对象拥有大数据量的业务对象的时候，可以考虑分块进行处理 ，然后解决一块释放一块的策略。
   8、**不要在经常调用的方法中创建对象** ，尤其是忌讳在循环中创建对象。可以适当的使用hashtable，vector 创建一组对象容器，然后从容器中去取那些对象，而不用每次new之后又丢弃。
   9、一般都是发生在开启大型文件或跟数据库一次拿了太多的数据，造成 Out Of Memory Error 的状况，这时就大概要计算一下数据量的最大值是多少，并且设定所需最小及最大的内存空间值。
   10、**尽量少用finalize函数 ，因为finalize()会加大GC的工作量，而GC相当于耗费系统的计算能力。**
  11、不要过滥使用哈希表 ，有一定开发经验的开发人员经常会使用hash表（hash表在JDK中的一个实现就是HashMap）来缓存一些数据，从而提高系统的运行速度。比如使用HashMap缓存一些物料信息、人员信息等基础资料，这在提高系统速度的同时也加大了系统的内存占用，特别是当缓存的资料比较多的时候。其实我们可以使用操作系统中的缓存的概念来解决这个问题，也就是给被缓存的分配一个一定大小的缓存容器，按照一定的算法淘汰不需要继续缓存的对象，这样一方面会因为进行了对象缓存而提高了系统的运行效率，同时由于缓存容器不是无限制扩大，从而也减少了系统的内存占用。现在有很多开源的缓存实现项目，比如ehcache、oscache等，这些项目都实现了FIFO、MRU等常见的缓存算法



## Drawble学习

在从 XML 中创建一个 Drawable 时，步骤如下：

先根据 XML 节点名称来决定创造什么类型的 Drawable;然后 new 出相应的 Drawable;
再为该 Drawable 调用 inflate 方法，让其把配置加载起来——因为每种 Drawable 会重写 inflate 方法，所以，可以正确加载到各项配置及属性。
setAlpha方法用于设置一个 Drawable 的透明度，setBounds用来指定当执行绘制时，在 Canvas 上的位置和区域。比如我们自定义一个 View，在其onDraw中绘制一个BitmapDrawable，我们设置了 BitmapDrawable 的 Alpha 和 Bounds，代码如下：

```java
Drawable baseDrawable = getResources().getDrawable(R.drawable.base);
baseDrawable.setAlpha(100);
baseDrawable.setBounds(10, 20, 500, 300);
imageContent.setDrawable(baseDrawable);


```

1、Drawable 简介
Drawable——可简单理解为可绘制物，表示一些可以绘制在 Canvas 上的对象。

```
Drawable
    |- createFromPath
    |- createFromResourceStream
    |- createFromStream
    |- createFromXml
    |
    |- inflate   : 从XML中解析属性，子类需重写
    |- setAlpha  : 设置绘制时的透明度
    |- setBounds : 设置Canvas为Drawable提供的绘制区域
    |- setLevel  : 控制Drawable的Level值，这个值在ClipDrawable、RotateDrawable、ScaleDrawable、AnimationDrawable等Drawable中有重要作用；区间为[0, 10000]
    |- draw(Canvas) : 绘制到Canvas上，子类必须重写
```

inflate 方法用于从 XML 中读取 Drawable 的配置，

draw 方法则实现了把一个 Drawable 确切的绘制到一个 Canvas 上面——draw 方法为一个abstract抽象方法。


setAlpha方法用于设置一个 Drawable 的透明度，setBounds用来指定当执行绘制时，在 Canvas 上的位置和区域。比如我们自定义一个 View，在其onDraw中绘制一个BitmapDrawable，我们设置了 BitmapDrawable 的 Alpha 和 Bounds，代码如下：

```java
Drawable baseDrawable = getResources().getDrawable(R.drawable.base);
baseDrawable.setAlpha(100);
baseDrawable.setBounds(10, 20, 500, 300);
```

 
