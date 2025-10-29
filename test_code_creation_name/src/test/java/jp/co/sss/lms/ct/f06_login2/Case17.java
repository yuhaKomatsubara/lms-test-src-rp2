package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
		//画面遷移
		goTo("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// ログイン要素の取得
		WebElement id = webDriver.findElement(By.name("loginId"));
		WebElement password = webDriver.findElement(By.name("password"));

		//クリア処理
		id.clear();
		password.clear();

		//ログイン情報の入力
		id.sendKeys("StudentAA02");
		password.sendKeys("StudentAA02");

		//ログインボタンを押下
		WebElement buttonElement = webDriver.findElement(By.className("btn-primary"));
		buttonElement.click();

		//URLチェック
		assertThat(webDriver.getTitle(), is(containsString("セキュリティ規約")));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		//チェックボックスにチェック
		WebElement check = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[1]/div/label"));
		check.click();

		getEvidence(new Object() {
		}, "01");

		scrollBy("150");
		//次へを押下
		WebElement next = webDriver.findElement(By.className("btn-primary"));
		next.click();

		getEvidence(new Object() {
		}, "02");
		WebElement agreeError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2"));
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		// 現在のパスワードを入力
		WebElement currentPass = webDriver.findElement(By.name("currentPassword"));
		currentPass.clear();
		currentPass.sendKeys("StudentAA02");

		//新しいパスワード
		WebElement newPass = webDriver.findElement(By.name("password"));
		newPass.clear();
		newPass.sendKeys("StudentAA002");

		//確認パスワード
		WebElement checkPass = webDriver.findElement(By.name("passwordConfirm"));
		checkPass.clear();
		checkPass.sendKeys("StudentAA002");

		getEvidence(new Object() {
		}, "01");

		scrollBy("150");

		//変更ボタンを押下
		WebElement submit = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		submit.click();

		//アラートチェック
		WebElement modalChk = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", modalChk);

		getEvidence(new Object() {
		}, "02");

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		}, "3");
	}

}
