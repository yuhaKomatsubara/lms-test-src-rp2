package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
		//ログイン要素の取得
		WebElement id = webDriver.findElement(By.name("loginId"));
		WebElement password = webDriver.findElement(By.name("password"));

		//クリア処理
		id.clear();
		password.clear();

		//ログイン情報の入力
		id.sendKeys("StudentAA01");
		password.sendKeys("StudentAA001");

		//ログインボタンを押下
		WebElement button = webDriver.findElement(By.className("btn-primary"));
		button.click();

		//URLチェック
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//勤怠管理画面に遷移
		WebElement attendanc = webDriver.findElement(By.linkText("勤怠"));
		attendanc.click();

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		//URLチェック・エビデンス
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//出勤ボタンを押下
		WebElement push = webDriver.findElement(By.name("punchIn"));
		push.click();

		// 5秒待つ
		pageLoadTimeout(5);

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		WebElement button = webDriver.findElement(By.className("alert-dismissible"));
		assertEquals("×\n勤怠情報の登録が完了しました。", button.getText());

		// 出勤完了メッセージのエビデンス取得
		getEvidence(new Object() {
		}, "01");

		pageLoadTimeout(10);

		WebElement tabele = webDriver.findElement(By.tagName("tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].scrollHeight;", tabele);

		scrollBy("window.innerHeight");
		pageLoadTimeout(8);
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		scrollTo("0");
		//退勤ボタンを押下
		WebElement push = webDriver.findElement(By.name("punchOut"));
		push.click();

		// 5秒待つ
		pageLoadTimeout(5);

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		WebElement comp = webDriver.findElement(By.className("alert-dismissible"));
		assertEquals("×\n勤怠情報の登録が完了しました。", comp.getText());

		// 退勤完了のエビデンス取得
		getEvidence(new Object() {
		}, "01");

		WebElement table = webDriver.findElement(By.tagName("tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].scrollHeight;", table);

		scrollBy("window.innerHeight");
		pageLoadTimeout(8);
		getEvidence(new Object() {
		}, "02");
	}

}
