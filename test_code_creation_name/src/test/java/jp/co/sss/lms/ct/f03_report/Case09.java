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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
		// ログインID及びパスワードの処理
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		WebElement loginPass = webDriver.findElement(By.name("password"));

		loginId.clear();
		loginPass.clear();
		loginId.sendKeys("StudentAA01");
		loginPass.sendKeys("StudentAA001");

		//ログインボタンを押下
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		//URLチェック
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//ユーザー詳細情報に遷移
		WebElement userName = webDriver.findElement(By.xpath("//a[small[text()='ようこそ受講生ＡＡ１さん']]"));
		userName.click();

		//画面遷移したかを確認する
		WebElement userDetail = webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", userDetail.getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		scrollBy("900");

		//「修正」ボタンを押下
		WebElement editButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		editButton.click();

		//エビデンスを取得
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//学習理解度クリア
		WebElement learningItems = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		learningItems.clear();
		getEvidence(new Object() {
		}, "01");

		scrollBy("600");

		//「提出」ボタンを押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		scrollBy("1");

		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		WebElement inputError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[1]/fieldset/div/div[1]/p/span"));
		assertThat(inputError.getText(), is(containsString("理解度を入力した場合は、学習項目は必須です。")));

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 「学習項目」の内容を修正
		WebElement learningItems = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		learningItems.clear();
		learningItems.sendKeys("週報テスト");

		//「学習項目」の理解度をクリアする
		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByValue("");

		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール		
		scrollBy("560");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		//画面上部にスクロール
		scrollTo("1");

		//エビデンス取得
		getEvidence(new Object() {
		}, "02");

		//エラーメッセージチェック
		WebElement selectError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[1]/fieldset/div/div[2]/p/span"));
		assertThat(selectError.getText(), is(containsString("学習項目を入力した場合は、理解度は必須です。")));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//「学習項目」の内容を修正
		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByValue("");

		//「目標の達成度」を入力
		WebElement scoreInput = webDriver.findElement(By.name("contentArray[0]"));
		scoreInput.clear();
		scoreInput.sendKeys("テストです");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール		
		scrollBy("560");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		scrollTo("230");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーチェック
		WebElement inputError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputError.getText(), is(containsString("目標の達成度は半角数字で入力してください。")));

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// 「目標達成度」の修正（範囲外）
		WebElement scoreInput = webDriver.findElement(By.name("contentArray[0]"));
		scoreInput.clear();
		scoreInput.sendKeys("15");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール		
		scrollBy("500");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		scrollTo("230");

		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーチェック
		WebElement inputError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputError.getText(), is(containsString("目標の達成度は、半角数字で、1～10の範囲内で入力してください。")));
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// 「目標の達成度」の修正（未入力）
		WebElement scoreInput = webDriver.findElement(By.name("contentArray[0]"));
		scoreInput.clear();

		//「所感」のクリア
		WebElement impression = webDriver.findElement(By.name("contentArray[1]"));
		impression.clear();

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール		
		scrollBy("600");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		getEvidence(new Object() {
		}, "02");
		//目標の達成度エラーメッセージチェック
		WebElement inputError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputError.getText(), is(containsString("目標の達成度は半角数字で入力してください。")));

		//所感エラーメッセージチェック
		//		WebElement inputError2 = webDriver
		//				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[2]/div/p/span"));
		//		assertThat(inputError2.getText(), is(containsString("所感は必須です。")));

		WebElement impressionErrorMessage = webDriver
				.findElement(By.xpath("//p/span[text()='* 所感は必須です。']"));
		assertEquals("* 所感は必須です。", impressionErrorMessage.getText());
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// 「目標達成度」の修正
		WebElement scoreInput = webDriver.findElement(By.name("contentArray[0]"));
		scoreInput.clear();
		scoreInput.sendKeys("5");

		//文字列の生成
		StringBuilder strA = new StringBuilder();
		StringBuilder strB = new StringBuilder();
		for (int i = 0; i < 2001; i++) {
			strA.append("A");
			strB.append("B");
		}

		//「所感」の修正
		WebElement impression = webDriver.findElement(By.name("contentArray[1]"));
		impression.clear();
		impression.sendKeys(strA);

		//入力待ち時間
		pageLoadTimeout(2000);

		//一週間の振り返り
		WebElement weekInput = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		weekInput.clear();
		weekInput.sendKeys(strB);

		//入力待ち時間
		pageLoadTimeout(2000);

		//エヴィデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール		
		scrollBy("500");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();

		getEvidence(new Object() {
		}, "02");

		//所感エラーチェック
		WebElement inputImpressionError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[2]/div/p/span"));
		assertThat(inputImpressionError.getText(), is(containsString("所感の長さが最大値(2000)を超えています。")));

		//一週間の振り返りエラーちぇく
		WebElement inputWeekError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[3]/div/p/span"));
		assertThat(inputWeekError.getText(), is(containsString("一週間の振り返りの長さが最大値(2000)を超えています。")));
	}

}
