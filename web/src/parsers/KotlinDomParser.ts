import { XmlUtil } from '../utils/XmlUtil';

// Kotlin DOM解析器 - 基于Java版本改写为Kotlin代码生成
export class KotlinDomParser {
  private bufCode: string[] = [];
  private text: string = '';
  private count: number = 0;

  constructor() {}

  setXmlText(text: string): void {
    this.text = text;
  }

  // 解析为Kotlin代码
  parseKotlin(): void {
    try {
      const parser = new DOMParser();
      const document = parser.parseFromString(this.text, 'text/xml');
      
      const root = document.documentElement;
      if (root) {
        this.setNodesId(root);
        const layoutRoot = 'layoutRoot';
        const className = root.tagName;
        
        this.bufCode = [];
        this.bufCode.push('    val context = this');
        this.bufCode.push(`    val ${layoutRoot} = ${className}(context)`);
        this.printKotlinCode(className, layoutRoot, root);
        this.bufCode.push(`    setContentView(${layoutRoot})`);
      }
    } catch (error) {
      console.error('解析XML错误:', error);
    }
  }

  // 遍历节点设置ID
  private setNodesId(node: Element): void {
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
      element.setAttribute('layoutparams', 'layoutParams' + this.count);
      this.count++;
    }
    
    for (let i = 0; i < nodelist.length; i++) {
      const child = nodelist[i];
      if (child.nodeType === Node.ELEMENT_NODE) {
        this.setNodesId(child as Element);
      }
    }
  }

  // 获取颜色处理
  private getColor(value: string): string {
    if (value.startsWith('#')) {
      return XmlUtil.getColorHex(value);
    } else if (value.startsWith('@color/')) {
      return `R.color.${value.substring(7)}`;
    } else if (value.startsWith('?attr/')) {
      return `ContextCompat.getColor(context, R.attr.${value.substring(6)})`;
    }
    return value;
  }

  // 输出Kotlin代码
  private printKotlinCode(className: string, name: string, node: Element): void {
    const nodelist = node.children;
    
    if (node.nodeType === Node.ELEMENT_NODE) {
      const element = node as Element;
      const layoutName = element.getAttribute('name') || '';
      
      this.bufCode.push(`    val ${layoutName} = ${element.tagName}(context)`);
      
      // 处理layout_width和layout_height
      let layoutWidth = element.getAttribute('android:layout_width') || '';
      let layoutHeight = element.getAttribute('android:layout_height') || '';
      const layoutWeight = element.getAttribute('android:layout_weight') || '';
      
      if (layoutWidth === 'match_parent' || layoutWidth === 'fill_parent') {
        layoutWidth = 'ViewGroup.LayoutParams.MATCH_PARENT';
      } else if (layoutWidth === 'wrap_content') {
        layoutWidth = 'ViewGroup.LayoutParams.WRAP_CONTENT';
      } else {
        layoutWidth = XmlUtil.getSize(layoutWidth);
      }
      
      if (layoutHeight === 'match_parent' || layoutHeight === 'fill_parent') {
        layoutHeight = 'ViewGroup.LayoutParams.MATCH_PARENT';
      } else if (layoutHeight === 'wrap_content') {
        layoutHeight = 'ViewGroup.LayoutParams.WRAP_CONTENT';
      } else {
        layoutHeight = XmlUtil.getSize(layoutHeight);
      }
      
      // 创建LayoutParams
      const layoutParams = element.getAttribute('layoutparams') || '';
      if (className === 'LinearLayout') {
        this.bufCode.push(`    val ${layoutParams} = ${className}.LayoutParams(${layoutWidth}, ${layoutHeight})`);
      } else {
        this.bufCode.push(`    val ${layoutParams} = ViewGroup.MarginLayoutParams(${layoutWidth}, ${layoutHeight})`);
      }
      
      if (layoutWeight) {
        this.bufCode.push(`    ${layoutParams}.weight = ${layoutWeight}f`);
      }
      
      // 处理margin
      this.handleMargin(element, layoutParams);
      
      // 处理padding
      this.handlePadding(element, layoutName);
      
      // 处理其他属性
      this.handleAttributes(element, layoutName, layoutParams);
      
      // 递归处理子节点
      for (let i = 0; i < nodelist.length; i++) {
        const child = nodelist[i];
        if (child.nodeType === Node.ELEMENT_NODE) {
          this.printKotlinCode(element.tagName, layoutName, child as Element);
        }
      }
      
      // 添加到父容器
      this.bufCode.push(`    ${name}.addView(${layoutName}, ${layoutParams})`);
    }
  }

  // 处理margin属性
  private handleMargin(element: Element, layoutParams: string): void {
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
      
      this.bufCode.push(`    ${layoutParams}.topMargin = ${marginTop}`);
      this.bufCode.push(`    ${layoutParams}.bottomMargin = ${marginBottom}`);
      this.bufCode.push(`    ${layoutParams}.leftMargin = ${marginLeft}`);
      this.bufCode.push(`    ${layoutParams}.rightMargin = ${marginRight}`);
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
      
      this.bufCode.push(`    ${layoutName}.setPadding(${paddingLeft}, ${paddingTop}, ${paddingRight}, ${paddingBottom})`);
    }
  }

  // 处理其他属性
  private handleAttributes(element: Element, layoutName: string, layoutParams: string): void {
    const attributes = element.attributes;
    
    for (let i = 0; i < attributes.length; i++) {
      const attr = attributes[i];
      const key = attr.name;
      const value = attr.value;
      
      switch (key) {
        case 'android:background':
          if (value.startsWith('#')) {
            this.bufCode.push(`    ${layoutName}.setBackgroundColor(${XmlUtil.getColorHex(value)})`);
          } else if (value.startsWith('@color/')) {
            this.bufCode.push(`    ${layoutName}.setBackgroundColor(ContextCompat.getColor(context, R.color.${value.substring(7)}))`);
          } else if (value.startsWith('@drawable/')) {
            this.bufCode.push(`    ${layoutName}.background = ContextCompat.getDrawable(context, R.drawable.${value.substring(10)})`);
          }
          break;
          
        case 'android:orientation':
          if (value === 'vertical') {
            this.bufCode.push(`    ${layoutName}.orientation = LinearLayout.VERTICAL`);
          } else if (value === 'horizontal') {
            this.bufCode.push(`    ${layoutName}.orientation = LinearLayout.HORIZONTAL`);
          }
          break;
          
        case 'android:text':
          if (value.startsWith('@string/')) {
            this.bufCode.push(`    ${layoutName}.setText(R.string.${value.substring(8)})`);
          } else {
            this.bufCode.push(`    ${layoutName}.text = "${value}"`);
          }
          break;
          
        case 'android:textColor':
          if (value.startsWith('#')) {
            this.bufCode.push(`    ${layoutName}.setTextColor(${XmlUtil.getColorHex(value)})`);
          } else if (value.startsWith('@color/')) {
            this.bufCode.push(`    ${layoutName}.setTextColor(ContextCompat.getColor(context, R.color.${value.substring(7)}))`);
          }
          break;
          
        case 'android:textSize':
          this.bufCode.push(`    ${layoutName}.textSize = ${XmlUtil.getFontSize(value)}`);
          break;
          
        case 'android:src':
        case 'app:srcCompat':
          const drawable = XmlUtil.getDrawable(value);
          this.bufCode.push(`    ${layoutName}.setImageDrawable(ContextCompat.getDrawable(context, ${drawable}))`);
          break;
          
        case 'android:gravity':
          let gravity = '';
          if (value.includes('center')) gravity += ' or Gravity.CENTER';
          if (value.includes('left')) gravity += ' or Gravity.LEFT';
          if (value.includes('right')) gravity += ' or Gravity.RIGHT';
          if (value.includes('top')) gravity += ' or Gravity.TOP';
          if (value.includes('bottom')) gravity += ' or Gravity.BOTTOM';
          if (gravity) {
            this.bufCode.push(`    ${layoutName}.gravity = ${gravity.substring(4)}`);
          }
          break;
          
        case 'android:id':
          if (value.startsWith('@+id/') || value.startsWith('@id/')) {
            this.bufCode.push(`    ${layoutName}.id = R.id.${value.substring(value.indexOf('/') + 1)}`);
          }
          break;
      }
    }
  }

  toString(): string {
    return this.bufCode.join('\n');
  }
}