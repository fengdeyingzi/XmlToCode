import com.xl.util.DomParser;
import com.xl.util.SwiftDomParser;
import com.xl.util.UIUtil;
import com.xl.window.XmlToCodeWindow;

public class XmlParserMain {
	public static void main(String[] args) {
		SwiftDomParser parser = new SwiftDomParser();
		parser.parseSwift();
		UIUtil.setWindowsStyle();
		XmlToCodeWindow window = new XmlToCodeWindow();
		window.show();
	}
}
