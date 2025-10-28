package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		// 画面遷移
		goTo("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログイン要素の取得
		WebElement id = webDriver.findElement(By.name("loginId"));
		WebElement password = webDriver.findElement(By.name("password"));

		//クリア処理
		id.clear();
		password.clear();

		//ログイン情報の入力
		id.sendKeys("StudentAA03");
		password.sendKeys("StudentAA003");

		//ログインボタンを押下
		WebElement buttonElement = webDriver.findElement(By.className("btn-primary"));
		buttonElement.click();

		//URLチェック
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// 勤怠管理画面に遷移
		WebElement attendanc = webDriver.findElement(By.linkText("勤怠"));
		attendanc.click();

		//エビデンス取得
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//勤怠情報直接変更画面に遷移
		WebElement directEntry = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/p/a"));
		directEntry.click();

		//エビデンス取得
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		WebElement table = webDriver.findElement(By.tagName("tbody"));

		//「提示」ボタンの取得
		List<WebElement> buttons = table.findElements(By.tagName("button"));
		for (WebElement b : buttons) {
			b.click();
			scrollBy("130");
		}

		//エビデンス取得
		pageLoadTimeout(10);
		getEvidence(new Object() {
		}, "01");

		pageLoadTimeout(10);

		//更新ボタンの押下
		WebElement form = webDriver.findElement(By.tagName("form"));
		WebElement updateComp = form.findElement(By.xpath("//input[@value='更新']"));
		updateComp.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

		WebElement compText = webDriver.findElement(By.className("alert-dismissible"));
		assertEquals("×\n勤怠情報の登録が完了しました。", compText.getText());

		getEvidence(new Object() {
		}, "02");
	}

}
