package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms");

		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインID及びパスワード入力要素の取得
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		WebElement loginPassword = webDriver.findElement(By.name("password"));

		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();

		// ログインID及びパスワード
		loginId.sendKeys("StudentAA01");
		loginPassword.sendKeys("StudentAA001");

		// ログインボタン押下
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//ドロップダウンをクリック
		WebElement dropdownElement = webDriver.findElement(By.className("dropdown"));
		dropdownElement.click();

		//ヘルプボタンをクリック
		WebElement helpButton = webDriver.findElement(By.linkText("ヘルプ"));
		helpButton.click();

		WebElement transitionHelp = webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", transitionHelp.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//現在のタブを記録
		String currentHundle = webDriver.getWindowHandle();

		//現在開いているタブを記録
		Set<String> beforeHandles = webDriver.getWindowHandles();

		WebElement question = webDriver.findElement(By.linkText("よくある質問"));
		question.click();

		//新しく開いたタブに切り替え
		Set<String> afterHandles = WebDriverUtils.webDriver.getWindowHandles();
		for (String handle : afterHandles) {
			if (!beforeHandles.contains(handle)) {
				WebDriverUtils.webDriver.switchTo().window(handle);
				break;
			}
		}

		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//キーワードの入力要素お取得
		WebElement keyword = webDriver.findElement(By.name("keyword"));

		//キーワードのクリア
		keyword.clear();

		//キーワード入力
		keyword.sendKeys("助成金");

		//検索ボタン押下
		WebElement keywordButton = webDriver.findElement(By.className("btn-primary"));
		keywordButton.click();

		getEvidence(new Object() {
		}, "1");

		// 画面スクロール・待ち処理5秒
		scrollBy("window.innerHeight");
		pageLoadTimeout(5);

		WebElement result = webDriver.findElement(By.className("mb10"));
		assertEquals("Q.助成金書類の作成方法が分かりません", result.getText());

		getEvidence(new Object() {
		}, "2");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//クリアボタン押下
		WebElement clea = webDriver.findElement(By.cssSelector("input[type='button']"));
		clea.click();
		scrollTo("0");
		pageLoadTimeout(5);

		//「クリア」ボタンを押下後にキーワード入力フォームが空になっているのを確認
		WebElement keyword = webDriver.findElement(By.name("keyword"));
		assertEquals("", keyword.getText());

		getEvidence(new Object() {
		});
	}

}
