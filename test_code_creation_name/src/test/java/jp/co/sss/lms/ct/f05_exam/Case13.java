package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
		id.sendKeys("StudentAA02");
		password.sendKeys("StudentAA002");

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//試験有
		WebElement detail = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
		detail.click();

		getEvidence(new Object() {
		});
		assertThat(webDriver.getTitle(), is(containsString("セクション詳細")));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		// 詳細へ
		WebElement detail = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[1]/tbody/tr[2]/td[2]/form/input[1]"));
		detail.click();

		//エビデンス取得
		getEvidence(new Object() {
		});
		assertThat(webDriver.getTitle(), is(containsString("試験【ITリテラシー①】")));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		// 試験開始ボタンを押下する
		WebElement testStart = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/form/input[4]"));
		testStart.click();

		//エビデンス取得
		getEvidence(new Object() {
		});
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		//待ち処理(100秒)
		pageLoadTimeout(150);

		//画面下部にスクロール
		scrollBy("4500");

		getEvidence(new Object() {
		}, "01");

		//確認画面へ進むボタン押下
		WebElement check = webDriver
				.findElement(By.xpath("//*[@id=\"examQuestionForm\"]/div[13]/fieldset/input"));
		check.click();

		getEvidence(new Object() {
		}, "02");
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		scrollBy("1800");
		getEvidence(new Object() {
		}, "01");

		//解答を送信するボタンを押下
		WebElement sendButton = webDriver
				.findElement(By.id("sendButton"));
		sendButton.click();

		//アラートチェック
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

		getEvidence(new Object() {
		}, "02");
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));
		WebElement score = webDriver.findElement(By.xpath("//*[@id=\"examBeing\"]/h2/small"));
		assertThat(score.getText(), is(containsString("0.0点")));
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		scrollBy("5100");

		getEvidence(new Object() {
		}, "01");

		//戻るボタンを押下
		WebElement back = webDriver
				.findElement(By.xpath("//*[@id=\"examBeing\"]/div[13]/fieldset/form/input[1]"));
		back.click();

		getEvidence(new Object() {
		}, "01");

		pageLoadTimeout(5);
		assertEquals("http://localhost:8080/lms/exam/start", webDriver.getCurrentUrl());
	}

}
