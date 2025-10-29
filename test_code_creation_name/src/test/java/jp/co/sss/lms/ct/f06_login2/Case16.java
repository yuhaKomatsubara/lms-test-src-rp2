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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
		scrollBy("");

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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		// 現在のパスワードを入力
		WebElement currentPass = webDriver.findElement(By.name("currentPassword"));
		currentPass.clear();

		//新しいパスワード
		WebElement newPass = webDriver.findElement(By.name("password"));
		newPass.clear();

		//確認パスワード
		WebElement checkPass = webDriver.findElement(By.name("passwordConfirm"));
		checkPass.clear();

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

		//エラーチェック
		WebElement currentPassError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span"));
		assertThat(currentPassError.getText(), is(containsString("現在のパスワードは必須です。")));
		WebElement newPassError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPassError.getText(), is(containsString("パスワードは必須です。")));

		WebElement passConfirmError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[3]/div/ul/li/span"));
		assertThat(passConfirmError.getText(), is(containsString("確認パスワードは必須です。")));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// 現在のパスワードを入力
		WebElement currentPass = webDriver.findElement(By.name("currentPassword"));
		currentPass.clear();
		currentPass.sendKeys("StudentAA02");

		//新しいパスワード
		WebElement newPass = webDriver.findElement(By.name("password"));
		newPass.clear();
		newPass.sendKeys("StudentAA00000000000000000000");

		//確認パスワード
		WebElement checkPass = webDriver.findElement(By.name("passwordConfirm"));
		checkPass.clear();
		checkPass.sendKeys("StudentAA00000000000000000000");

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

		//エラーチェック
		WebElement newPassError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPassError.getText(), is(containsString("パスワードの長さが最大値(20)を超えています。")));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// 現在のパスワードを入力
		WebElement currentPass = webDriver.findElement(By.name("currentPassword"));
		currentPass.clear();
		currentPass.sendKeys("StudentAA02");

		//新しいパスワード
		WebElement newPass = webDriver.findElement(By.name("password"));
		newPass.clear();
		newPass.sendKeys("0000000000");

		//確認パスワード
		WebElement checkPass = webDriver.findElement(By.name("passwordConfirm"));
		checkPass.clear();
		checkPass.sendKeys("0000000000");

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

		//エラーチェック
		WebElement newPassError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPassError.getText(),
				is(containsString("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。")));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		// 現在のパスワードを入力
		WebElement currentPass = webDriver.findElement(By.name("currentPassword"));
		currentPass.clear();
		currentPass.sendKeys("StudentAA02");

		//新しいパスワード
		WebElement newPass = webDriver.findElement(By.name("password"));
		newPass.clear();
		newPass.sendKeys("StudentAA004");

		//確認パスワード
		WebElement checkPass = webDriver.findElement(By.name("passwordConfirm"));
		checkPass.clear();
		checkPass.sendKeys("StudentAA044");

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

		//エラーチェック
		WebElement newPassError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPassError.getText(),
				is(containsString("パスワードと確認パスワードが一致しません。")));
	}

}
