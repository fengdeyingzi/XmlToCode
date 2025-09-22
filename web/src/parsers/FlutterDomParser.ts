import { XmlUtil } from '../utils/XmlUtil';

// Flutter代码解析器 - 对应Java版本的FlutterDomParser
export class FlutterDomParser {
  private bufCode: string[] = [];
  private text: string = '';
  private count: number = 0;

  constructor() {}

  setXmlText(text: string): void {
    this.text = text;
  }

  // 解析为Flutter代码
  parseFlutter(): void {
    try {
      const parser = new DOMParser();
      const document = parser.parseFromString(this.text, 'text/xml');
      
      const root = document.documentElement;
      if (root) {
        this.setNodesId(null, root);
        this.bufCode = [];
        this.printFlutterCode(root, 1);
      }
    } catch (error) {
      console.error('解析XML错误:', error);
    }
  }

  // 遍历节点设置ID
  private setNodesId(eleParent: Element | null, node: Element): void {
    const nodelist = node.children;
    
    if (node.nodeType === Node.ELEMENT_NODE) {
      const element = node as Element;
      const id = node.tagName;
      const simpleName = id.includes('.') ? id.substring(id.lastIndexOf('.') + 1) : id;
      
      let viewName = simpleName.toLowerCase() + '_' + this.count;
      const keyId = element.getAttribute('android:id');
      
      if (keyId && (keyId.startsWith('@id/') || keyId.startsWith('@+id/'))) {
        viewName = keyId.substring(keyId.indexOf('/') + 1);
      }
      
      element.setAttribute('id', simpleName.toLowerCase() + '_' + this.count);
      element.setAttribute('name', viewName);
      element.setAttribute('layoutparams', 'layoutParams_' + this.count);
      this.count++;
    }
    
    for (let i = 0; i < nodelist.length; i++) {
      const child = nodelist[i];
      if (child.nodeType === Node.ELEMENT_NODE) {
        this.setNodesId(node, child as Element);
      }
    }
  }

  // 输出Flutter代码
  private printFlutterCode(element: Element, level: number): void {
    const nodeName = element.tagName;
    const indent = '  '.repeat(level);
    
    // 获取Flutter控件名
    const flutterWidgetName = this.getFlutterWidgetName(nodeName);
    
    // 开始控件
    this.bufCode.push(`${indent}${flutterWidgetName}(`);
    
    // 处理属性
    this.handleFlutterAttributes(element, level + 1);
    
    // 处理子控件
    const children = XmlUtil.getChildElements(element);
    if (children.length > 0) {
      if (this.isContainerWidget(flutterWidgetName)) {
        if (children.length === 1) {
          this.bufCode.push(`${indent}  child:`);
          this.printFlutterCode(children[0], level + 1);
        } else {
          this.bufCode.push(`${indent}  children: [`);
          children.forEach((child, index) => {
            this.printFlutterCode(child, level + 2);
            if (index < children.length - 1) {
              this.bufCode.push(',');
            }
          });
          this.bufCode.push(`${indent}  ],`);
        }
      }
    }
    
    this.bufCode.push(`${indent}),`);
  }

  // 获取Flutter控件名
  private getFlutterWidgetName(nodeName: string): string {
    switch (nodeName) {
      case 'LinearLayout': return 'Column'; // 或 Row，根据orientation决定
      case 'FrameLayout': return 'Stack';
      case 'RelativeLayout': return 'Stack';
      case 'TextView': return 'Text';
      case 'EditText': return 'TextField';
      case 'Button': return 'ElevatedButton';
      case 'ImageView': return 'Image';
      case 'ScrollView': return 'SingleChildScrollView';
      case 'ListView': return 'ListView';
      case 'View': return 'Container';
      default:
        if (nodeName.includes('.')) {
          return nodeName.substring(nodeName.lastIndexOf('.') + 1);
        }
        return 'Container';
    }
  }

  // 判断是否为容器控件
  private isContainerWidget(widgetName: string): boolean {
    const containerWidgets = ['Column', 'Row', 'Stack', 'Container', 'SingleChildScrollView', 'ListView'];
    return containerWidgets.includes(widgetName);
  }

  // 处理Flutter属性
  private handleFlutterAttributes(element: Element, level: number): void {
    const indent = '  '.repeat(level);
    const nodeName = element.tagName;
    const flutterWidgetName = this.getFlutterWidgetName(nodeName);
    const attributes = element.attributes;
    
    // 处理orientation for LinearLayout
    if (nodeName === 'LinearLayout') {
      const orientation = element.getAttribute('android:orientation') || 'vertical';
      if (orientation === 'horizontal') {
        // 需要重新确定为Row
        this.bufCode[this.bufCode.length - 1] = this.bufCode[this.bufCode.length - 1].replace('Column', 'Row');
      }
    }
    
    // 收集样式属性
    const styleProps: string[] = [];
    
    for (let i = 0; i < attributes.length; i++) {
      const attr = attributes[i];
      const key = attr.name;
      const value = attr.value;
      
      switch (key) {
        case 'android:layout_width':
        case 'android:layout_height':
          this.handleFlutterSize(key, value, styleProps);
          break;
          
        case 'android:background':
          this.handleFlutterBackground(value, styleProps);
          break;
          
        case 'android:text':
          if (flutterWidgetName === 'Text') {
            this.bufCode.push(`${indent}'${value}',`);
          } else if (flutterWidgetName === 'ElevatedButton') {
            this.bufCode.push(`${indent}child: Text('${value}'),`);
          }
          break;
          
        case 'android:textColor':
          this.handleFlutterTextColor(value, styleProps);
          break;
          
        case 'android:textSize':
          this.handleFlutterTextSize(value, styleProps);
          break;
          
        case 'android:src':
          if (flutterWidgetName === 'Image') {
            let imagePath = value;
            if (value.startsWith('@drawable/')) {
              imagePath = value.substring(10);
            } else if (value.startsWith('@mipmap/')) {
              imagePath = value.substring(8);
            }
            this.bufCode.push(`${indent}image: AssetImage('assets/images/${imagePath}.png'),`);
          }
          break;
          
        case 'android:gravity':
          this.handleFlutterGravity(value, styleProps);
          break;
          
        case 'android:padding':
        case 'android:paddingLeft':
        case 'android:paddingTop':
        case 'android:paddingRight':
        case 'android:paddingBottom':
          this.handleFlutterPadding(element, styleProps);
          break;
          
        case 'android:layout_margin':
        case 'android:layout_marginLeft':
        case 'android:layout_marginTop':
        case 'android:layout_marginRight':
        case 'android:layout_marginBottom':
          this.handleFlutterMargin(element, styleProps);
          break;
      }
    }
    
    // 应用样式
    if (styleProps.length > 0) {
      if (flutterWidgetName === 'Text') {
        this.bufCode.push(`${indent}style: TextStyle(`);
        styleProps.forEach(prop => {
          this.bufCode.push(`${indent}  ${prop}`);
        });
        this.bufCode.push(`${indent}),`);
      } else if (flutterWidgetName === 'Container') {
        this.bufCode.push(`${indent}decoration: BoxDecoration(`);
        styleProps.forEach(prop => {
          this.bufCode.push(`${indent}  ${prop}`);
        });
        this.bufCode.push(`${indent}),`);
      }
    }
  }

  // 处理尺寸
  private handleFlutterSize(key: string, value: string, styleProps: string[]): void {
    if (value === 'match_parent') {
      if (key === 'android:layout_width') {
        styleProps.push('width: double.infinity,');
      } else if (key === 'android:layout_height') {
        styleProps.push('height: double.infinity,');
      }
    } else if (value === 'wrap_content') {
      // Flutter中wrap_content是默认行为
    } else {
      const size = XmlUtil.atoi(value);
      if (key === 'android:layout_width') {
        styleProps.push(`width: ${size}.0,`);
      } else if (key === 'android:layout_height') {
        styleProps.push(`height: ${size}.0,`);
      }
    }
  }

  // 处理背景
  private handleFlutterBackground(value: string, styleProps: string[]): void {
    if (value.startsWith('#')) {
      const color = this.convertColorToFlutter(value);
      styleProps.push(`color: Color(${color}),`);
    } else if (value.startsWith('@color/')) {
      styleProps.push(`color: Colors.${value.substring(7)},`);
    } else if (value.startsWith('@drawable/')) {
      const imageName = value.substring(10);
      styleProps.push(`image: DecorationImage(image: AssetImage('assets/images/${imageName}.png')),`);
    }
  }

  // 处理文本颜色
  private handleFlutterTextColor(value: string, styleProps: string[]): void {
    if (value.startsWith('#')) {
      const color = this.convertColorToFlutter(value);
      styleProps.push(`color: Color(${color}),`);
    } else if (value.startsWith('@color/')) {
      styleProps.push(`color: Colors.${value.substring(7)},`);
    }
  }

  // 处理文本大小
  private handleFlutterTextSize(value: string, styleProps: string[]): void {
    const size = XmlUtil.atoi(value);
    styleProps.push(`fontSize: ${size}.0,`);
  }

  // 处理对齐
  private handleFlutterGravity(value: string, styleProps: string[]): void {
    if (value.includes('center')) {
      styleProps.push('textAlign: TextAlign.center,');
    } else if (value.includes('left')) {
      styleProps.push('textAlign: TextAlign.left,');
    } else if (value.includes('right')) {
      styleProps.push('textAlign: TextAlign.right,');
    }
  }

  // 处理内边距
  private handleFlutterPadding(element: Element, styleProps: string[]): void {
    const padding = element.getAttribute('android:padding');
    const paddingLeft = element.getAttribute('android:paddingLeft');
    const paddingTop = element.getAttribute('android:paddingTop');
    const paddingRight = element.getAttribute('android:paddingRight');
    const paddingBottom = element.getAttribute('android:paddingBottom');
    
    if (padding) {
      const size = XmlUtil.atoi(padding);
      styleProps.push(`padding: EdgeInsets.all(${size}.0),`);
    } else if (paddingLeft || paddingTop || paddingRight || paddingBottom) {
      const left = paddingLeft ? XmlUtil.atoi(paddingLeft) : 0;
      const top = paddingTop ? XmlUtil.atoi(paddingTop) : 0;
      const right = paddingRight ? XmlUtil.atoi(paddingRight) : 0;
      const bottom = paddingBottom ? XmlUtil.atoi(paddingBottom) : 0;
      styleProps.push(`padding: EdgeInsets.only(left: ${left}.0, top: ${top}.0, right: ${right}.0, bottom: ${bottom}.0),`);
    }
  }

  // 处理外边距
  private handleFlutterMargin(element: Element, styleProps: string[]): void {
    const margin = element.getAttribute('android:layout_margin');
    const marginLeft = element.getAttribute('android:layout_marginLeft');
    const marginTop = element.getAttribute('android:layout_marginTop');
    const marginRight = element.getAttribute('android:layout_marginRight');
    const marginBottom = element.getAttribute('android:layout_marginBottom');
    
    if (margin) {
      const size = XmlUtil.atoi(margin);
      styleProps.push(`margin: EdgeInsets.all(${size}.0),`);
    } else if (marginLeft || marginTop || marginRight || marginBottom) {
      const left = marginLeft ? XmlUtil.atoi(marginLeft) : 0;
      const top = marginTop ? XmlUtil.atoi(marginTop) : 0;
      const right = marginRight ? XmlUtil.atoi(marginRight) : 0;
      const bottom = marginBottom ? XmlUtil.atoi(marginBottom) : 0;
      styleProps.push(`margin: EdgeInsets.only(left: ${left}.0, top: ${top}.0, right: ${right}.0, bottom: ${bottom}.0),`);
    }
  }

  // 转换颜色到Flutter格式
  private convertColorToFlutter(color: string): string {
    if (color.startsWith('#')) {
      const hex = color.substring(1);
      if (hex.length === 6) {
        return `0xFF${hex.toUpperCase()}`;
      } else if (hex.length === 8) {
        return `0x${hex.toUpperCase()}`;
      }
    }
    return '0xFF000000';
  }

  toString(): string {
    return this.bufCode.join('\n');
  }
}