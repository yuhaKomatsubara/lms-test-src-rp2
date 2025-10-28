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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		id.sendKeys("StudentAA04");
		password.sendKeys("StudentAA04");

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		scrollBy("100");
		//次へを押下
		WebElement next = webDriver.findElement(By.className("btn-primary"));
		next.click();

		getEvidence(new Object() {
		}, "01");

		scrollBy("150");

		//エビデンス取得・エラーチェック
		getEvidence(new Object() {
		}, "02");
		WebElement agreeError = webDriver.findElement(By.className("error"));
		assertThat(agreeError.getText(), is(containsString("セキュリティ規約への同意は必須です。")));

	}

}
