package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
		//ログイン要素を取得
		WebElement id = webDriver.findElement(By.name("loginId"));
		WebElement password = webDriver.findElement(By.name("password"));

		//クリア処理
		id.clear();
		password.clear();

		//ログイン情報の入力
		id.sendKeys("StudentAA02");
		password.sendKeys("StudentAA002");

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//提出済みの研修日を取得
		WebElement detailButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
		detailButton.click();

		//URLチェック
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//画面をスクロール
		scrollTo("450");

		//「提出済み週報【デモ】を確認する」ボタンを押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		submitButton.click();

		//		WebElement submit = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
		//		submit.click();

		//レポート登録画面に遷移したか確認
		assertThat(webDriver.getTitle(), is(containsString("レポート登録")));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 画面をスクロール
		scrollTo("500");

		//「所感」の修正
		WebElement inputA = webDriver.findElement(By.name("contentArray[1]"));
		inputA.clear();
		inputA.sendKeys("週報テストサンプル");

		//「週報」の修正
		WebElement inputB = webDriver.findElement(By.name("contentArray[2]"));
		inputB.clear();
		inputB.sendKeys("週報テスト");

		//「提出」ボタンを押下
		WebElement report = webDriver.findElement(By.className("btn-primary"));
		report.click();

		//セクション詳細画面に遷移確認
		WebElement section = webDriver.findElement(By.tagName("h2"));
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", section.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// ユーザー詳細画面に遷移
		WebElement userName = webDriver.findElement(By.xpath("//a[small[text()='ようこそ受講生ＡＡ２さん']]"));
		userName.click();
		//ユーザー詳細画面に遷移しているかを確認する
		WebElement userDetail = webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", userDetail.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// スクロール
		scrollBy("900");

		//「詳細」ボタンを押下
		WebElement detailbutton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/table[2]/tbody/tr[2]/td[5]/form[1]/input[1]"));
		detailbutton.click();

		//「達成度」の修正後表示チェック
		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td"));
		assertEquals("3", achievementLevel.getText());

		//「所感」の修正後表示チェック
		WebElement impression = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[2]/td"));
		assertEquals("週報テストサンプル", impression.getText());

		//「一週間の振り返り」修正後表示をチェック
		WebElement week = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[3]/td"));
		assertEquals("週報テスト", week.getText());

		getEvidence(new Object() {
		});

	}

}
