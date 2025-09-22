import './styles/main.css';
import { DomParser } from './parsers/DomParser';
import { SwiftDomParser } from './parsers/SwiftDomParser';
import { FlutterDomParser } from './parsers/FlutterDomParser';
import { FindViewByIdParser } from './parsers/FindViewByIdParser';
import { KotlinDomParser } from './parsers/KotlinDomParser';

// 应用程序主类
class XmlToCodeApp {
  private xmlInput!: HTMLTextAreaElement;
  private codeOutput!: HTMLPreElement;
  private toast!: HTMLElement;
  
  // 解析器实例
  private domParser!: DomParser;
  private swiftParser!: SwiftDomParser;
  private flutterParser!: FlutterDomParser;
  private findViewByIdParser!: FindViewByIdParser;
  private kotlinParser!: KotlinDomParser;

  constructor() {
    this.initializeElements();
    this.initializeParsers();
    this.bindEvents();
    this.setDefaultXml();
  }

  // 初始化DOM元素
  private initializeElements(): void {
    this.xmlInput = document.getElementById('xml-input') as HTMLTextAreaElement;
    this.codeOutput = document.getElementById('code-output') as HTMLPreElement;
    this.toast = document.getElementById('toast') as HTMLElement;
  }

  // 初始化解析器
  private initializeParsers(): void {
    this.domParser = new DomParser();
    this.swiftParser = new SwiftDomParser();
    this.flutterParser = new FlutterDomParser();
    this.findViewByIdParser = new FindViewByIdParser();
    this.kotlinParser = new KotlinDomParser();
  }

  // 绑定事件
  private bindEvents(): void {
    // 解析按钮事件
    document.getElementById('findViewById-btn')?.addEventListener('click', () => {
      this.parseWithHandler(() => this.parseFindViewById());
    });
    
    document.getElementById('java-btn')?.addEventListener('click', () => {
      this.parseWithHandler(() => this.parseJava());
    });
    
    document.getElementById('kotlin-btn')?.addEventListener('click', () => {
      this.parseWithHandler(() => this.parseKotlin());
    });
    
    document.getElementById('swift-btn')?.addEventListener('click', () => {
      this.parseWithHandler(() => this.parseSwift());
    });
    
    document.getElementById('flutter-btn')?.addEventListener('click', () => {
      this.parseWithHandler(() => this.parseFlutter());
    });

    // 文件操作事件
    document.getElementById('load-file-btn')?.addEventListener('click', () => {
      document.getElementById('file-input')?.click();
    });
    
    document.getElementById('file-input')?.addEventListener('change', (e) => {
      this.handleFileLoad(e);
    });
    
    document.getElementById('clear-btn')?.addEventListener('click', () => {
      this.clearInput();
    });
    
    document.getElementById('copy-btn')?.addEventListener('click', () => {
      this.copyCode();
    });
    
    document.getElementById('download-btn')?.addEventListener('click', () => {
      this.downloadCode();
    });
    
    // 关闭输出面板事件
    document.getElementById('close-output-btn')?.addEventListener('click', () => {
      this.hideOutputPanel();
    });

    // 拖拽事件
    this.bindDragEvents();
  }

  // 绑定拖拽事件
  private bindDragEvents(): void {
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
      this.xmlInput.addEventListener(eventName, this.preventDefaults, false);
      document.body.addEventListener(eventName, this.preventDefaults, false);
    });

    ['dragenter', 'dragover'].forEach(eventName => {
      this.xmlInput.addEventListener(eventName, () => {
        this.xmlInput.classList.add('drag-over');
      }, false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
      this.xmlInput.addEventListener(eventName, () => {
        this.xmlInput.classList.remove('drag-over');
      }, false);
    });

    this.xmlInput.addEventListener('drop', (e) => {
      this.handleFileDrop(e);
    }, false);
  }

  // 阻止默认事件
  private preventDefaults(e: Event): void {
    e.preventDefault();
    e.stopPropagation();
  }

  // 设置默认XML
  private setDefaultXml(): void {
    const defaultXml = `<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" 
    android:layout_width="match_parent"
    android:gravity="center"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/text_info"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:gravity="center"
        android:text="XML转代码\\nhttps://github.com/fengdeyingzi/XmlToCode"
        android:autoLink="web"
        android:padding="32dp" />
        
    <TextView
        android:id="@+id/text_author"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:text="风的影子 制作" />

</LinearLayout>`;
    
    this.xmlInput.value = defaultXml;
  }

  // 解析处理器包装
  private parseWithHandler(parseFunction: () => void): void {
    try {
      this.showLoading(true);
      parseFunction();
      this.showOutputPanel();
      this.showToast('解析成功!', 'success');
    } catch (error) {
      console.error('解析错误:', error);
      this.showToast('解析失败: ' + (error as Error).message, 'error');
    } finally {
      this.showLoading(false);
    }
  }

  // 解析findViewById
  private parseFindViewById(): void {
    const xmlText = this.xmlInput.value.trim();
    if (!xmlText) {
      throw new Error('请输入XML内容');
    }
    
    this.findViewByIdParser.setXmlText(xmlText);
    this.findViewByIdParser.parse();
    this.codeOutput.textContent = this.findViewByIdParser.toString();
  }

  // 解析Java代码
  private parseJava(): void {
    const xmlText = this.xmlInput.value.trim();
    if (!xmlText) {
      throw new Error('请输入XML内容');
    }
    
    this.domParser.setXmlText(xmlText);
    this.domParser.parseJava();
    this.codeOutput.textContent = this.domParser.toString();
  }

  // 解析Kotlin代码
  private parseKotlin(): void {
    const xmlText = this.xmlInput.value.trim();
    if (!xmlText) {
      throw new Error('请输入XML内容');
    }
    
    this.kotlinParser.setXmlText(xmlText);
    this.kotlinParser.parseKotlin();
    this.codeOutput.textContent = this.kotlinParser.toString();
  }

  // 解析Swift代码
  private parseSwift(): void {
    const xmlText = this.xmlInput.value.trim();
    if (!xmlText) {
      throw new Error('请输入XML内容');
    }
    
    this.swiftParser.setXmlText(xmlText);
    this.swiftParser.parseSwift();
    this.codeOutput.textContent = this.swiftParser.toString();
  }

  // 解析Flutter代码
  private parseFlutter(): void {
    const xmlText = this.xmlInput.value.trim();
    if (!xmlText) {
      throw new Error('请输入XML内容');
    }
    
    this.flutterParser.setXmlText(xmlText);
    this.flutterParser.parseFlutter();
    this.codeOutput.textContent = this.flutterParser.toString();
  }

  // 处理文件加载
  private handleFileLoad(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    
    if (file && file.name.endsWith('.xml')) {
      this.readFile(file);
    } else {
      this.showToast('请选择XML文件', 'error');
    }
  }

  // 处理文件拖拽
  private handleFileDrop(event: DragEvent): void {
    const files = event.dataTransfer?.files;
    
    if (files && files.length > 0) {
      const file = files[0];
      if (file.name.endsWith('.xml')) {
        this.readFile(file);
      } else {
        this.showToast('请拖拽XML文件', 'error');
      }
    }
  }

  // 读取文件内容
  private readFile(file: File): void {
    const reader = new FileReader();
    reader.onload = (e) => {
      const content = e.target?.result as string;
      this.xmlInput.value = content;
      this.showToast('文件加载成功', 'success');
    };
    reader.onerror = () => {
      this.showToast('文件读取失败', 'error');
    };
    reader.readAsText(file, 'UTF-8');
  }

  // 清空输入
  private clearInput(): void {
    this.xmlInput.value = '';
    this.codeOutput.textContent = '';
    this.showToast('内容已清空', 'success');
  }

  // 复制代码
  private async copyCode(): Promise<void> {
    const codeText = this.codeOutput.textContent;
    if (!codeText) {
      this.showToast('没有可复制的代码', 'error');
      return;
    }

    try {
      await navigator.clipboard.writeText(codeText);
      this.showToast('代码已复制到剪贴板', 'success');
      this.codeOutput.classList.add('highlight');
      setTimeout(() => {
        this.codeOutput.classList.remove('highlight');
      }, 500);
    } catch (err) {
      // 兜底方案
      this.copyToClipboardFallback(codeText);
      this.showToast('代码已复制到剪贴板', 'success');
    }
  }

  // 复制到剪贴板兜底方案
  private copyToClipboardFallback(text: string): void {
    const textArea = document.createElement('textarea');
    textArea.value = text;
    textArea.style.position = 'fixed';
    textArea.style.left = '-999999px';
    textArea.style.top = '-999999px';
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();
    document.execCommand('copy');
    textArea.remove();
  }

  // 下载代码
  private downloadCode(): void {
    const codeText = this.codeOutput.textContent;
    if (!codeText) {
      this.showToast('没有可下载的代码', 'error');
      return;
    }

    const blob = new Blob([codeText], { type: 'text/plain;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = 'generated_code.txt';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    
    this.showToast('代码下载成功', 'success');
  }

  // 显示提示消息
  private showToast(message: string, type: 'success' | 'error' | 'info' = 'info'): void {
    const toastMessage = this.toast.querySelector('.toast-message') as HTMLElement;
    
    if (toastMessage) {
      toastMessage.textContent = message;
    } else {
      this.toast.textContent = message;
    }
    
    this.toast.className = `toast show ${type}`;
    
    setTimeout(() => {
      this.toast.classList.remove('show');
    }, 3000);
  }

  // 显示/隐藏加载状态
  private showLoading(show: boolean): void {
    const buttons = document.querySelectorAll('.btn-action');
    buttons.forEach(btn => {
      if (show) {
        btn.classList.add('loading');
        (btn as HTMLButtonElement).disabled = true;
      } else {
        btn.classList.remove('loading');
        (btn as HTMLButtonElement).disabled = false;
      }
    });
  }
  
  // 显示输出面板（全屏模式）
  private showOutputPanel(): void {
    const outputPanel = document.getElementById('output-panel');
    if (outputPanel) {
      outputPanel.classList.remove('hidden');
      outputPanel.classList.add('fullscreen');
    }
  }
  
  // 隐藏输出面板
  private hideOutputPanel(): void {
    const outputPanel = document.getElementById('output-panel');
    if (outputPanel) {
      outputPanel.classList.add('hidden');
      outputPanel.classList.remove('fullscreen');
    }
  }
}

// 全局函数 - 显示帮助
function showHelp(): void {
  const modal = document.getElementById('help-modal');
  if (modal) {
    modal.classList.add('show');
  }
}

// 全局函数 - 隐藏帮助
function hideHelp(): void {
  const modal = document.getElementById('help-modal');
  if (modal) {
    modal.classList.remove('show');
  }
}

// 将函数绑定到window对象以便HTML调用
(window as any).showHelp = showHelp;
(window as any).hideHelp = hideHelp;

// 页面加载完成后初始化应用
document.addEventListener('DOMContentLoaded', () => {
  new XmlToCodeApp();
});