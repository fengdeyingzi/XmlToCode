import com.xl.util.DomParser;
import com.xl.util.UIUtil;
import com.xl.window.XmlToCodeWindow;

public class XmlParserMain {
	public static void main(String[] args) {
		DomParser parser = new DomParser();
		parser.parseSwift();
		UIUtil.setWindowsStyle();
		XmlToCodeWindow window = new XmlToCodeWindow();
		window.show();
	}
}
