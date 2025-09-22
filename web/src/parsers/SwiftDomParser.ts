import { XmlUtil } from '../utils/XmlUtil';

// Swift代码解析器 - 对应Java版本的SwiftDomParser
export class SwiftDomParser {
  private bufCode: string[] = [];
  private text: string = '';
  private count: number = 0;

  constructor() {}

  setXmlText(text: string): void {
    this.text = text;
  }

  // 解析为Swift代码
  parseSwift(): void {
    try {
      const parser = new DOMParser();
      const document = parser.parseFromString(this.text, 'text/xml');
      
      const root = document.documentElement;
      if (root) {
        this.setNodesId(null, root);
        const layoutRoot = 'layout_root';
        const className = root.tagName;
        
        this.bufCode = [];
        const swiftClassName = this.getLayoutName(className);
        
        if (className === 'LinearLayout') {
          const orientation = root.getAttribute('android:orientation') || '';
          let orientationValue = '.horz';
          if (orientation === 'vertical') {
            orientationValue = '.vert';
          } else if (orientation === 'horizontal') {
            orientationValue = '.horz';
          }
          
          this.bufCode.push(`    var ${layoutRoot}:${swiftClassName} = ${swiftClassName}(${orientationValue})`);
        } else {
          this.bufCode.push(`    var ${layoutRoot}:${swiftClassName} = ${swiftClassName}()`);
        }
        
        let layoutWidth = root.getAttribute('android:layout_width') || '';
        let layoutHeight = root.getAttribute('android:layout_height') || '';
        
        if (layoutWidth === 'match_parent' || layoutWidth === 'fill_parent') {
          layoutWidth = '.fill';
        } else if (layoutWidth === 'wrap_content') {
          layoutWidth = '.wrap';
        } else {
          layoutWidth = XmlUtil.getSize(layoutWidth);
        }
        
        if (layoutHeight === 'match_parent' || layoutHeight === 'fill_parent') {
          layoutHeight = '.fill';
        } else if (layoutHeight === 'wrap_content') {
          layoutHeight = '.wrap';
        } else {
          layoutHeight = XmlUtil.getSize(layoutHeight);
        }
        
        this.bufCode.push(`    ${layoutRoot}.tg_width ~= ${layoutWidth}`);
        this.bufCode.push(`    ${layoutRoot}.tg_height ~= ${layoutHeight}`);
        
        this.printiOSCode(className, layoutRoot, root);
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

  // 通过标签获取Swift类名
  private getLayoutName(nodeName: string): string {
    switch (nodeName) {
      case 'LinearLayout': return 'TGLinearLayout';
      case 'FrameLayout': return 'TGFrameLayout';
      case 'TextView': return 'TextView';
      case 'Button': return 'Button';
      case 'ImageView': return 'ImageView';
      case 'EditText': return 'EditText';
      case 'RelativeLayout': return 'TGRelativeLayout';
      case 'TableLayout': return 'TGTableLayout';
      case 'ListView': return 'UITableView';
      case 'View': return 'UIView';
      case 'ScrollView': return 'ScrollView';
      case 'HorizontalScrollView': return 'HorizontalScrollView';
      default:
        if (nodeName.includes('.')) {
          return nodeName.substring(nodeName.lastIndexOf('.') + 1);
        }
        return nodeName;
    }
  }

  // 输出iOS Swift代码
  private printiOSCode(className: string, name: string, node: Element): void {
    const nodelist = node.children;
    
    if (node.nodeType === Node.ELEMENT_NODE) {
      const element = node as Element;
      const layoutName = element.getAttribute('name') || '';
      const nodeName = element.tagName;
      
      // 处理layout_width和layout_height
      let layoutWidth = element.getAttribute('android:layout_width') || '';
      let layoutHeight = element.getAttribute('android:layout_height') || '';
      const orientation = element.getAttribute('android:orientation') || '';
      
      if (layoutWidth === 'match_parent' || layoutWidth === 'fill_parent') {
        layoutWidth = '.fill';
      } else if (layoutWidth === 'wrap_content') {
        layoutWidth = '.wrap';
      } else {
        layoutWidth = XmlUtil.getSize(layoutWidth);
      }
      
      if (layoutHeight === 'match_parent' || layoutHeight === 'fill_parent') {
        layoutHeight = '.fill';
      } else if (layoutHeight === 'wrap_content') {
        layoutHeight = '.wrap';
      } else {
        layoutHeight = XmlUtil.getSize(layoutHeight);
      }
      
      let orientationValue = '.horz';
      if (orientation === 'vertical') {
        orientationValue = '.vert';
      } else if (orientation === 'horizontal') {
        orientationValue = '.horz';
      }
      
      // 创建控件
      if (nodeName === 'LinearLayout') {
        this.bufCode.push(`    var ${layoutName}:TGLinearLayout = TGLinearLayout(${orientationValue})`);
      } else if (nodeName === 'FrameLayout') {
        this.bufCode.push(`    var ${layoutName}:TGFrameLayout = TGFrameLayout(${orientationValue})`);
      } else if (nodeName === 'ImageView') {
        this.bufCode.push(`    var ${layoutName}:UIImageView = UIImageView()`);
      } else {
        const swiftClassName = this.getLayoutName(nodeName);
        this.bufCode.push(`    var ${layoutName}:${swiftClassName} = ${swiftClassName}()`);
      }
      
      // 设置宽高
      if (layoutWidth.includes('%')) {
        this.bufCode.push(`    ${layoutName}.tg_width ~= ${layoutWidth}`);
      } else {
        this.bufCode.push(`    ${layoutName}.tg_width.equal(${layoutWidth})`);
      }
      
      if (layoutHeight.includes('%')) {
        this.bufCode.push(`    ${layoutName}.tg_height ~= ${layoutHeight}`);
      } else {
        this.bufCode.push(`    ${layoutName}.tg_height.equal(${layoutHeight})`);
      }
      
      // 处理margin
      this.handleMargin(element, layoutName);
      
      // 处理padding
      this.handlePadding(element, layoutName);
      
      // 处理其他属性
      this.handleAttributes(element, layoutName, nodeName);
      
      // 递归处理子节点
      for (let i = 0; i < nodelist.length; i++) {
        const child = nodelist[i];
        if (child.nodeType === Node.ELEMENT_NODE) {
          this.printiOSCode(element.tagName, layoutName, child as Element);
        }
      }
      
      // 添加到父容器
      if (className === 'ScrollView' || className === 'HorizontalScrollView') {
        this.bufCode.push(`    ${name}.addView(${layoutName})`);
      } else {
        this.bufCode.push(`    ${name}.addSubview(${layoutName})`);
      }
    }
  }

  // 处理margin属性
  private handleMargin(element: Element, layoutName: string): void {
    let margin = element.getAttribute('android:layout_margin') || '';
    let marginTop = element.getAttribute('android:layout_marginTop') || '';
    let marginBottom = element.getAttribute('android:layout_marginBottom') || '';
    let marginLeft = element.getAttribute('android:layout_marginLeft') || '';
    let marginRight = element.getAttribute('android:layout_marginRight') || '';
    
    if (margin) {
      marginTop = margin;
      marginLeft = margin;
      marginRight = margin;
      marginBottom = margin;
    }
    
    if (marginLeft || marginTop || marginRight || marginBottom) {
      marginLeft = XmlUtil.getSize(marginLeft);
      marginTop = XmlUtil.getSize(marginTop);
      marginRight = XmlUtil.getSize(marginRight);
      marginBottom = XmlUtil.getSize(marginBottom);
      
      this.bufCode.push(`    ${layoutName}.tg_top.equal(${marginTop})`);
      this.bufCode.push(`    ${layoutName}.tg_bottom.equal(${marginBottom})`);
      this.bufCode.push(`    ${layoutName}.tg_left.equal(${marginLeft})`);
      this.bufCode.push(`    ${layoutName}.tg_right.equal(${marginRight})`);
    }
  }

  // 处理padding属性
  private handlePadding(element: Element, layoutName: string): void {
    let padding = element.getAttribute('android:padding') || '';
    let paddingLeft = element.getAttribute('android:paddingLeft') || '';
    let paddingRight = element.getAttribute('android:paddingRight') || '';
    let paddingTop = element.getAttribute('android:paddingTop') || '';
    let paddingBottom = element.getAttribute('android:paddingBottom') || '';
    
    if (padding) {
      paddingLeft = padding;
      paddingTop = padding;
      paddingRight = padding;
      paddingBottom = padding;
    }
    
    if (paddingLeft || paddingTop || paddingBottom || paddingRight) {
      paddingLeft = XmlUtil.getSize(paddingLeft);
      paddingTop = XmlUtil.getSize(paddingTop);
      paddingRight = XmlUtil.getSize(paddingRight);
      paddingBottom = XmlUtil.getSize(paddingBottom);
      
      this.bufCode.push(`    ${layoutName}.tg_padding = UIEdgeInsets(top:CGFloat(${paddingTop}), left:CGFloat(${paddingLeft}), bottom:CGFloat(${paddingBottom}), right:CGFloat(${paddingRight}))`);
    }
  }

  // 处理其他属性
  private handleAttributes(element: Element, layoutName: string, nodeName: string): void {
    const attributes = element.attributes;
    
    for (let i = 0; i < attributes.length; i++) {
      const attr = attributes[i];
      const key = attr.name;
      const value = attr.value;
      
      switch (key) {
        case 'android:background':
          if (value.startsWith('#')) {
            this.bufCode.push(`    ${layoutName}.layer.backgroundColor = ColorUtil.getCGColor(${XmlUtil.getColorHex(value)})`);
          } else if (value.startsWith('@color/')) {
            this.bufCode.push(`    ${layoutName}.layer.backgroundColor = ColorUtil.getCGColor("${value.substring(7)}")`);
          } else if (value.startsWith('@drawable/')) {
            this.bufCode.push(`    ${layoutName}.layer.contents = UIImage(named: "${value.substring(10)}")!.cgImage`);
          } else if (value.startsWith('@mipmap/')) {
            this.bufCode.push(`    ${layoutName}.layer.contents = UIImage(named: "${value.substring(8)}")!.cgImage`);
          }
          break;
          
        case 'android:src':
          let imageName = value;
          if (value.startsWith('@drawable/')) {
            imageName = value.substring(10);
          } else if (value.startsWith('@mipmap/')) {
            imageName = value.substring(8);
          }
          this.bufCode.push(`    ${layoutName}.image = UIImage(named:"${imageName}")`);
          break;
          
        case 'android:text':
          if (value.startsWith('@string/')) {
            if (nodeName === 'Button') {
              this.bufCode.push(`    ${layoutName}.setTitle("${value.substring(8)}", for: UIControl.State.normal)`);
            } else {
              this.bufCode.push(`    ${layoutName}.text = ${value.substring(8)}`);
            }
          } else {
            if (nodeName === 'Button') {
              this.bufCode.push(`    ${layoutName}.setTitle("${value}", for: UIControl.State.normal)`);
            } else {
              this.bufCode.push(`    ${layoutName}.text = "${value}"`);
            }
          }
          break;
          
        case 'android:textColor':
          if (value.startsWith('#')) {
            this.bufCode.push(`    ${layoutName}.textColor = ColorUtil.hexToUIColor(${value})`);
          } else if (value.startsWith('@color/')) {
            this.bufCode.push(`    ${layoutName}.textColor = ColorUtil.getColor("${value.substring(7)}")`);
          }
          break;
          
        case 'android:textSize':
          if (value.includes('sp')) {
            const size = XmlUtil.atoi(value);
            this.bufCode.push(`    ${layoutName}.font = UIFont.systemFont(ofSize: ${size})`);
          } else {
            const fontSize = XmlUtil.getFontSize(value);
            this.bufCode.push(`    ${layoutName}.font = UIFont.systemFont(ofSize: CGFloat(${fontSize}))`);
          }
          break;
          
        case 'android:gravity':
          if (nodeName === 'TextView' || nodeName === 'EditText') {
            if (value.includes('left')) {
              this.bufCode.push(`    ${layoutName}.textAlignment = .left`);
            } else if (value.includes('right')) {
              this.bufCode.push(`    ${layoutName}.textAlignment = .right`);
            } else if (value.includes('center')) {
              this.bufCode.push(`    ${layoutName}.textAlignment = .center`);
            }
          } else {
            if (value.includes('center')) {
              this.bufCode.push(`    ${layoutName}.tg_gravity = TGGravity.center`);
            } else {
              if (value.includes('top')) {
                this.bufCode.push(`    ${layoutName}.tg_gravity = TGGravity.top`);
              }
              if (value.includes('bottom')) {
                this.bufCode.push(`    ${layoutName}.tg_gravity = TGGravity.bottom`);
              }
              if (value.includes('left')) {
                this.bufCode.push(`    ${layoutName}.tg_gravity = TGGravity.left`);
              }
              if (value.includes('right')) {
                this.bufCode.push(`    ${layoutName}.tg_gravity = TGGravity.right`);
              }
            }
          }
          break;
          
        case 'android:layout_gravity':
          let isLeft = false, isTop = false, isRight = false, isBottom = false;
          
          if (value.includes('left')) {
            isLeft = true;
            this.bufCode.push(`    ${layoutName}.tg_left ~= 0`);
          }
          if (value.includes('right')) {
            isRight = true;
            this.bufCode.push(`    ${layoutName}.tg_right ~= 0`);
          }
          if (value.includes('top')) {
            isTop = true;
            this.bufCode.push(`    ${layoutName}.tg_top ~= 0`);
          }
          if (value.includes('bottom')) {
            isBottom = true;
            this.bufCode.push(`    ${layoutName}.tg_bottom ~= 0`);
          }
          if (value.includes('center')) {
            if (isLeft || isRight) {
              this.bufCode.push(`    ${layoutName}.tg_centerY ~= 0`);
            } else if (isBottom || isTop) {
              this.bufCode.push(`    ${layoutName}.tg_centerX ~= 0`);
            } else {
              this.bufCode.push(`    ${layoutName}.tg_centerX ~= 0`);
              this.bufCode.push(`    ${layoutName}.tg_centerY ~= 0`);
            }
          }
          break;
          
        case 'android:visibility':
          if (value === 'visible') {
            this.bufCode.push(`    ${layoutName}.hidden = false`);
          } else if (value === 'invisible' || value === 'gone') {
            this.bufCode.push(`    ${layoutName}.hidden = true`);
          }
          break;
          
        case 'android:singleLine':
          if (value === 'true') {
            this.bufCode.push(`    ${layoutName}.numberOfLines = 1`);
          } else {
            this.bufCode.push(`    ${layoutName}.lineBreakMode = NSLineBreakMode.ByWordWrapping`);
            this.bufCode.push(`    ${layoutName}.numberOfLines = 0`);
          }
          break;
          
        case 'android:hint':
          if (value.startsWith('@string/')) {
            this.bufCode.push(`    ${layoutName}.placeholder = ${XmlUtil.getString(value)}`);
          } else {
            this.bufCode.push(`    ${layoutName}.placeholder = "${value}"`);
          }
          break;
      }
    }
  }

  toString(): string {
    return this.bufCode.join('\n');
  }
}