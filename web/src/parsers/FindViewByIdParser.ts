import { XmlUtil } from '../utils/XmlUtil';

// findViewById代码解析器 - 对应Java版本的FindViewByIdParser
export class FindViewByIdParser {
  private bufCode: string[] = [];
  private text: string = '';
  private count: number = 0;

  constructor() {}

  setXmlText(text: string): void {
    this.text = text;
  }

  // 解析findViewById代码
  parse(): void {
    try {
      const parser = new DOMParser();
      const document = parser.parseFromString(this.text, 'text/xml');
      
      const root = document.documentElement;
      if (root) {
        this.bufCode = [];
        this.parseElement(root);
      }
    } catch (error) {
      console.error('解析XML错误:', error);
    }
  }

  // 解析元素
  private parseElement(element: Element): void {
    // 处理当前元素
    const androidId = element.getAttribute('android:id');
    if (androidId && (androidId.startsWith('@+id/') || androidId.startsWith('@id/'))) {
      const idName = androidId.substring(androidId.indexOf('/') + 1);
      const className = this.getClassNameForTag(element.tagName);
      
      this.bufCode.push(`${className} ${idName} = findViewById(R.id.${idName});`);
    }
    
    // 递归处理子元素
    const children = XmlUtil.getChildElements(element);
    children.forEach(child => {
      this.parseElement(child);
    });
  }

  // 根据标签名获取Java类名
  private getClassNameForTag(tagName: string): string {
    switch (tagName) {
      case 'TextView': return 'TextView';
      case 'EditText': return 'EditText';
      case 'Button': return 'Button';
      case 'ImageView': return 'ImageView';
      case 'ImageButton': return 'ImageButton';
      case 'CheckBox': return 'CheckBox';
      case 'RadioButton': return 'RadioButton';
      case 'Switch': return 'Switch';
      case 'SeekBar': return 'SeekBar';
      case 'ProgressBar': return 'ProgressBar';
      case 'Spinner': return 'Spinner';
      case 'ListView': return 'ListView';
      case 'RecyclerView': return 'RecyclerView';
      case 'ScrollView': return 'ScrollView';
      case 'HorizontalScrollView': return 'HorizontalScrollView';
      case 'LinearLayout': return 'LinearLayout';
      case 'RelativeLayout': return 'RelativeLayout';
      case 'FrameLayout': return 'FrameLayout';
      case 'TableLayout': return 'TableLayout';
      case 'GridLayout': return 'GridLayout';
      case 'ConstraintLayout': return 'ConstraintLayout';
      case 'CoordinatorLayout': return 'CoordinatorLayout';
      case 'CardView': return 'CardView';
      case 'NestedScrollView': return 'NestedScrollView';
      case 'ViewPager': return 'ViewPager';
      case 'TabLayout': return 'TabLayout';
      case 'FloatingActionButton': return 'FloatingActionButton';
      case 'Toolbar': return 'Toolbar';
      case 'AppBarLayout': return 'AppBarLayout';
      case 'CollapsingToolbarLayout': return 'CollapsingToolbarLayout';
      case 'WebView': return 'WebView';
      default:
        // 处理自定义View或完整包名的View
        if (tagName.includes('.')) {
          return tagName;
        }
        return 'View';
    }
  }

  toString(): string {
    return this.bufCode.join('\n');
  }
}