package com.nightowldevelopers.playpurchase;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.inapplib.InAppDataModel;
import com.inapplib.InAppPurchase;
import com.inapplib.OnPaymentListener;
import com.util.Purchase;


public class MainActivity extends AppCompatActivity implements OnPaymentListener {

    private static final String TAG = "MainActivity";
    private InAppPurchase inAppPurchase;
    private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnUfU/dD9Od2hIbhuc3GeE83a7a3KszH5pF2BnnEG/aBdo3p/ZonSE8vdygHKvZEU3oRqYrAvajr3g1QQQ/IsPhkvyY6BU07mFyif6EKQF1vU2WxHNyrfCnG3Fo7recsK6pvk8BFeZl+feNTnz2jRXEyobd077QizwU/mKJTSsOx0JnJmCbStmDeFn49VzFKMfN+W6R23RKW+suzMbHi03wfeY2bpJSWA7j2Lkh2TCKzU+w6jh1wsPlNq8Z84id6ugajtk85w3pXQeSyofA0OxOnqs0sahrUcrOSmXDNye6CXD7pDcg/zn918byfzUz4mpdj7/WUkZ6z/Pbcrz6oeXwIDAQAB";
    private String SKU = "donate2";
    private String payLoad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inAppPurchase = new InAppPurchase(MainActivity.this, base64EncodedPublicKey,
                SKU, payLoad, this);
        inAppPurchase.setUpInApp();

        findViewById(R.id.btnPurchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inAppPurchase.subscriptionPurchase();
            }
        });
    }

    @Override
    public void onSuccessPayment() {
        Toast.makeText(this, "Success Purchase", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessPaymentDetails(InAppDataModel model) {
        Log.e(TAG,"SKU : "+model.getPackageName());
        Log.e(TAG,"Order ID : "+model.getOrderId());
    }

    @Override
    public void onFailurePayment(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAlreadyPurchase(Purchase purchase) {
        Log.e(TAG,"Already purchase : "+purchase.getSku());
        findViewById(R.id.btnPurchase).setVisibility(View.GONE);
        Toast.makeText(this, "onAlreadyPurchase " +purchase.getSku(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (InAppPurchase.RC_REQUEST == requestCode)
            inAppPurchase.InAppDetails(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inAppPurchase.unregisterReceiver();
    }
}
