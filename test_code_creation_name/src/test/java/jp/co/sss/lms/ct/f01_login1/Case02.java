package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {
	private WebDriver chromeDriver;

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
		// TODO ここに追加
		WebDriverUtils.goTo("http://localhost:8080/lms");

		assertEquals("ログイン | LMS", webDriver.getTitle());
		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// ログインID及びパスワード入力要素の取得
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		WebElement loginPassword = webDriver.findElement(By.name("password"));

		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();

		// ログインID及びパスワード
		loginId.sendKeys("Aaaa0000");
		loginPassword.sendKeys("Aaaa0000");

		// ログインボタン押下
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		getEvidence(new Object() {
		});

		WebElement error = webDriver.findElement(By.className("error"));
		assertEquals("* ログインに失敗しました。", error.getText());

	}

	//	@Test
	//	@Order(3)
	//	@DisplayName("テスト03 パスワード間違い")
	//	void test03() {
	//		// TODO ここに追加
	//		//画面遷移
	//		WebDriverUtils.goTo("http://localhost:8080/lms");
	//
	//		// ログインID及びパスワード入力要素の取得
	//		WebElement loginId = webDriver.findElement(By.name("loginId"));
	//		WebElement loginPassword = webDriver.findElement(By.name("password"));
	//
	//		// ログインID及びパスワードのクリア処理
	//		loginId.clear();
	//		loginPassword.clear();
	//
	//		// ログインID及びパスワード
	//		final String LOGIN_ID = ("StudentAA01");
	//		final String LOGIN_PASSWORD = ("Aaaa0000");
	//
	//		// ログインボタン押下
	//		loginPassword.sendKeys(Keys.ENTER);
	//
	//		getEvidence(new Object() {
	//		});
	//
	//		WebElement error = webDriver.findElement(By.className("error"));
	//		assertEquals("* ログインに失敗しました。", error.getText());
	//	}
	//
	//	@Test
	//	@Order(4)
	//	@DisplayName("テスト04 ログインIDの未入力")
	//	void test04() {
	//		// TODO ここに追加
	//		//画面遷移
	//		WebDriverUtils.goTo("http://localhost:8080/lms");
	//
	//		// ログインID及びパスワード入力要素の取得
	//		//		WebElement loginId = webDriver.findElement(By.name("loginId"));
	//		WebElement loginPassword = webDriver.findElement(By.name("password"));
	//
	//		// ログインID及びパスワードのクリア処理
	//		//		loginId.clear();
	//		loginPassword.clear();
	//
	//		// ログインID及びパスワード
	//		//		final String LOGIN_ID = ("StudentAA01");
	//		final String LOGIN_PASSWORD = ("Aaaa0000");
	//
	//		// ログインボタン押下
	//		loginPassword.sendKeys(Keys.ENTER);
	//
	//		getEvidence(new Object() {
	//		});
	//
	//		WebElement error = webDriver.findElement(By.className("error"));
	//		assertEquals("* ログインIDは必須です。", error.getText());
	//	}
	//
	//	@Test
	//	@Order(5)
	//	@DisplayName("テスト05 パスワードの未入力")
	//	void test05() {
	//		// TODO ここに追加
	//		//画面遷移
	//		WebDriverUtils.goTo("http://localhost:8080/lms");
	//
	//		// ログインID及びパスワード入力要素の取得
	//		WebElement loginId = webDriver.findElement(By.name("loginId"));
	//		//		WebElement loginPassword = webDriver.findElement(By.name("password"));
	//
	//		// ログインID及びパスワードのクリア処理
	//		loginId.clear();
	//		//		loginPassword.clear();
	//
	//		// ログインID及びパスワード
	//		final String LOGIN_ID = ("StudentAA01");
	//		//		final String LOGIN_PASSWORD = ("Aaaa0000");
	//
	//		// ログインボタン押下
	//		loginId.sendKeys(Keys.ENTER);
	//
	//		getEvidence(new Object() {
	//		});
	//
	//		WebElement error = webDriver.findElement(By.className("error"));
	//		assertEquals("* ログインIDは必須です。", error.getText());
	//	}
	//
	//	@Test
	//	@Order(6)
	//	@DisplayName("テスト06 IDとパスワード未入力")
	//	void test06() {
	//		// TODO ここに追加
	//		//画面遷移
	//		WebDriverUtils.goTo("http://localhost:8080/lms");
	//
	//		// ログインID及びパスワード入力要素の取得
	//		WebElement loginId = webDriver.findElement(By.name("loginId"));
	//		WebElement loginPassword = webDriver.findElement(By.name("password"));
	//
	//		// ログインID及びパスワードのクリア処理
	//		loginId.clear();
	//		loginPassword.clear();
	//
	//		// ログインID及びパスワード
	//		final String LOGIN_ID = ("");
	//		final String LOGIN_PASSWORD = ("");
	//
	//		// ログインボタン押下
	//		loginPassword.sendKeys(Keys.ENTER);
	//
	//		getEvidence(new Object() {
	//		});
	//
	//		WebElement error = webDriver.findElement(By.className("error"));
	//		assertEquals("* パスワードを入力してください。", error.getText());
	//	}

}
