package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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

		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		//エビデンス取得
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));
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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// 出勤時刻の変更
		Select attendanceHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		attendanceHour.selectByValue("9");
		Select attendanceMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		attendanceMin.selectByValue("");

		//退勤時間の変更
		Select leavingHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		leavingHour.selectByValue("18");
		Select leavingMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		leavingMin.selectByValue("");

		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//更新ボタンの押下
		WebElement update = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		update.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		scrollBy("600");
		getEvidence(new Object() {
		}, "02");

		//エビデンス取得
		WebElement error = webDriver.findElement(By.className("error"));

		//出勤エラーチェック
		WebElement attendanceError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span"));
		assertThat(attendanceError.getText(), is(containsString("出勤時間が正しく入力されていません。")));

		//退勤エラーチェック
		WebElement leavingError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[2]/span"));
		assertThat(leavingError.getText(), is(containsString("退勤時間が正しく入力されていません。")));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// 出勤時刻の変更
		Select attendanceHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		attendanceHour.selectByValue("");
		Select attendanceMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		attendanceMin.selectByValue("");

		//退勤時間の変更
		Select leavingHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		leavingHour.selectByValue("18");
		Select leavingMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		leavingMin.selectByValue("0");

		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//更新ボタンの押下
		WebElement update = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		update.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);

		getEvidence(new Object() {
		}, "02");

		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));

		//出勤エラーチェック
		WebElement error = webDriver.findElement(By.className("error"));
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", error.getText());
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		//出勤時刻の変更
		Select attendanceHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		attendanceHour.selectByValue("12");
		Select attendanceMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		attendanceMin.selectByValue("0");

		//退勤時間の変更
		Select leavingHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		leavingHour.selectByValue("10");
		Select leavingMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		leavingMin.selectByValue("0");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//更新ボタンの押下
		WebElement update = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		update.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);

		getEvidence(new Object() {
		}, "02");

		//出勤エラーチェック
		WebElement error = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span"));
		assertThat(error.getText(), is(containsString("退勤時刻[0]は出勤時刻[0]より後でなければいけません。")));

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		//出勤時刻の変更
		Select attendanceHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		attendanceHour.selectByValue("9");
		Select attendanceMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		attendanceMin.selectByValue("0");

		//退勤時間の変更
		Select leavingHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		leavingHour.selectByValue("12");
		Select leavingMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		leavingMin.selectByValue("0");

		//中抜け時間
		Select outTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		outTime.selectByValue("360");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//更新ボタンの押下
		WebElement update = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		update.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);

		//エビデンス取得・エラーチェック
		getEvidence(new Object() {
		}, "02");
		WebElement error = webDriver.findElement(By.className("error"));
		assertEquals("* 中抜け時間が勤務時間を超えています。", error.getText());
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		//出勤時刻の変更
		Select attendanceHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		attendanceHour.selectByValue("9");
		Select attendanceMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		attendanceMin.selectByValue("0");

		//退勤時間の変更
		Select leavingHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		leavingHour.selectByValue("18");
		Select leavingMin = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		leavingMin.selectByValue("0");

		//中抜け時間
		Select outTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		outTime.selectByValue("60");

		//備考欄
		WebElement note = webDriver.findElement(By.name("attendanceList[0].note"));

		//文字列の生成
		StringBuilder strA = new StringBuilder();
		for (int i = 0; i < 101; i++) {
			strA.append("A");
		}

		//入力・待ち時間
		note.sendKeys(strA);
		pageLoadTimeout(2000);

		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//更新ボタンの押下
		WebElement update = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		update.click();

		//アラートチェック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);

		//エビデンス取得・エラーチェック
		getEvidence(new Object() {
		}, "02");
		WebElement error = webDriver.findElement(By.className("error"));
		assertEquals("* 備考の長さが最大値(100)を超えています。", error.getText());
		assertEquals("http://localhost:8080/lms/attendance/update", webDriver.getCurrentUrl());

	}

}
