// XML工具类 - 对应Java版本的XmlUtil
export class XmlUtil {
  
  // 解析颜色值
  static getColor(text: string): number {
    let color = 0;
    const argb: number[] = [0, 0, 0, 0];
    let start = 0;
    let hex = 0;
    
    for (let i = 0; i < text.length; i++) {
      if (text.charAt(i) === '#') {
        start = i + 1;
        hex = text.length - start;
      }
    }
    
    if (hex === 3) {
      for (let i = 0; i < 3; i++) {
        const c = text.charAt(start + i);
        argb[0] = 0xff;
        if (c >= 'A' && c <= 'F') {
          argb[i + 1] = (c.charCodeAt(0) - 'A'.charCodeAt(0) + 10) * 16 + (c.charCodeAt(0) - 'A'.charCodeAt(0) + 10);
        } else if (c >= 'a' && c <= 'f') {
          argb[i + 1] = (c.charCodeAt(0) - 'a'.charCodeAt(0) + 10) * 16 + (c.charCodeAt(0) - 'a'.charCodeAt(0) + 10);
        } else if (c >= '0' && c <= '9') {
          argb[i + 1] = (c.charCodeAt(0) - '0'.charCodeAt(0)) * 16 + (c.charCodeAt(0) - '0'.charCodeAt(0));
        }
      }
    } else if (hex === 6) {
      argb[0] = 0xff;
      for (let i = 0; i < 3; i++) {
        const c = text.charAt(start + i * 2);
        const c2 = text.charAt(start + i * 2 + 1);
        
        if (c >= 'A' && c <= 'F') {
          argb[i + 1] = (c.charCodeAt(0) - 'A'.charCodeAt(0) + 10) << 4;
        } else if (c >= 'a' && c <= 'f') {
          argb[i + 1] = (c.charCodeAt(0) - 'a'.charCodeAt(0) + 10) << 4;
        } else if (c >= '0' && c <= '9') {
          argb[i + 1] = (c.charCodeAt(0) - '0'.charCodeAt(0)) << 4;
        }
        
        if (c2 >= 'A' && c2 <= 'F') {
          argb[i + 1] |= (c2.charCodeAt(0) - 'A'.charCodeAt(0) + 10);
        } else if (c2 >= 'a' && c2 <= 'f') {
          argb[i + 1] |= (c2.charCodeAt(0) - 'a'.charCodeAt(0) + 10);
        } else if (c2 >= '0' && c2 <= '9') {
          argb[i + 1] |= (c2.charCodeAt(0) - '0'.charCodeAt(0));
        }
      }
    } else if (hex === 8) {
      for (let i = 0; i < 4; i++) {
        const c = text.charAt(start + i * 2);
        const c2 = text.charAt(start + i * 2 + 1);
        
        if (c >= 'A' && c <= 'F') {
          argb[i] = (c.charCodeAt(0) - 'A'.charCodeAt(0) + 10) << 4;
        } else if (c >= 'a' && c <= 'f') {
          argb[i] = (c.charCodeAt(0) - 'a'.charCodeAt(0) + 10) << 4;
        } else if (c >= '0' && c <= '9') {
          argb[i] = (c.charCodeAt(0) - '0'.charCodeAt(0)) << 4;
        }
        
        if (c2 >= 'A' && c2 <= 'F') {
          argb[i] |= (c2.charCodeAt(0) - 'A'.charCodeAt(0) + 10);
        } else if (c2 >= 'a' && c2 <= 'f') {
          argb[i] |= (c2.charCodeAt(0) - 'a'.charCodeAt(0) + 10);
        } else if (c2 >= '0' && c2 <= '9') {
          argb[i] |= (c2.charCodeAt(0) - '0'.charCodeAt(0));
        }
      }
    }
    
    color = (argb[0] << 24) | (argb[1] << 16) | (argb[2] << 8) | argb[3];
    return color;
  }
  
  // 获取颜色十六进制字符串
  static getColorHex(text: string): string {
    return `0x${this.getColor(text).toString(16).padStart(8, '0')}`;
  }
  
  // 解析尺寸信息
  static getSize(text: string): string {
    if (text.startsWith('@dimen/')) {
      return `getResources().getDimensionPixelSize(R.dimen.${text.substring(text.lastIndexOf('/') + 1)})`;
    } else if (text.endsWith('dp') || text.endsWith('dip')) {
      return `DisplayUtil.dip2px(context, ${this.atoi(text)})`;
    } else if (text.endsWith('sp')) {
      return `DisplayUtil.sp2px(context, ${this.atoi(text)})`;
    } else if (text.endsWith('px')) {
      return `${this.atoi(text)}`;
    }
    return `${this.atoi(text)}`;
  }
  
  // 解析字体大小
  static getFontSize(text: string): string {
    if (text.startsWith('@dimen/')) {
      return `DisplayUtil.px2sp(context,getResources().getDimension(R.dimen.${text.substring(text.lastIndexOf('/') + 1)}))`;
    } else if (text.endsWith('dp') || text.endsWith('dip')) {
      return `DisplayUtil.dip2sp(context, ${this.atoi(text)})`;
    } else if (text.endsWith('sp')) {
      return `${this.atoi(text)}`;
    } else if (text.endsWith('px')) {
      return `DisplayUtil.px2sp(context, ${this.atoi(text)})`;
    }
    return `${this.atoi(text)}`;
  }
  
  // 解析字符串资源
  static getString(text: string): string {
    if (text.startsWith('@string/')) {
      return `R.string.${text.substring(text.lastIndexOf('/') + 1)}`;
    }
    return text;
  }
  
  // 解析drawable资源
  static getDrawable(value: string): string {
    if (value.startsWith('@drawable/')) {
      return `R.drawable.${value.substring(10)}`;
    } else if (value.startsWith('@mipmap/')) {
      return `R.mipmap.${value.substring(8)}`;
    } else if (value.startsWith('@android:drawable/')) {
      return `android.R.drawable.${value.substring(18)}`;
    }
    return value;
  }
  
  // 解析布尔值
  static getBoolean(text: string): boolean {
    return text === 'true';
  }
  
  // 解析浮点数
  static getFloat(text: string): number {
    return parseFloat(text) || 0;
  }
  
  // 解析整数
  static atoi(text: string): number {
    const match = text.match(/\d+/);
    return match ? parseInt(match[0], 10) : 0;
  }
  
  // 获取子元素
  static getChildElements(element: Element): Element[] {
    const children: Element[] = [];
    for (let i = 0; i < element.children.length; i++) {
      const child = element.children[i];
      if (child.nodeType === Node.ELEMENT_NODE) {
        children.push(child as Element);
      }
    }
    return children;
  }
}