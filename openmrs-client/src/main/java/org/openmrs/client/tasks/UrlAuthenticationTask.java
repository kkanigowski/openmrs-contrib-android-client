package org.openmrs.client.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.client.R;
import org.openmrs.client.activities.DashboardActivity;
import org.openmrs.client.applications.ACApplication;
import org.openmrs.client.utils.ApplicationConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlAuthenticationTask extends AsyncTask<Void, Void, Void> {

    private final String password;
    private final String userName;
    private final String url;

    private ProgressDialog progressDialog;
    private Context ctx;
    private static Logger logger = Logger.getLogger(UrlAuthenticationTask.class.getName());

    public UrlAuthenticationTask(Context ctx, String password) {

        this.ctx = ctx;
        this.password = password;
        this.userName = ACApplication.getInstance().getUsernamePreference();
        this.url = ACApplication.getInstance().getServerUrlPreference() + ApplicationConstants.SESSION_URL;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle(ctx.getString(R.string.processing));
        progressDialog.setMessage(ctx.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        RequestQueue queue = Volley.newRequestQueue(ACApplication.getInstance());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            String responseSessionId = "sessionId";
                            String responseAuthenticated = "authenticated";

                            ACApplication.getInstance().setSessionIdPreference(response.getString(responseSessionId));
                            Boolean isAuthenticated = Boolean.parseBoolean(response.getString(responseAuthenticated));

                            if (!isAuthenticated) {
                                Toast.makeText(ACApplication.getInstance(), ctx.getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent myIntent = new Intent(ctx, DashboardActivity.class);
                                ctx.startActivity(myIntent);
                            }

                        } catch (JSONException e) {
                            logger.log(Level.WARNING, e.toString());
                        }
                    }
                }
                , new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String sError = error.toString();
                        Log.d("RESPONSE", "request error: " + sError);
                        sError = sError.substring(sError.lastIndexOf(':') + 1);
                        Toast.makeText(ctx, sError.toString(), Toast.LENGTH_SHORT);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        String creds = String.format("%s:%s", userName, password);
                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        params.put("Authorization", auth);
                        return params;
                    }
                };
        queue.add(jsObjRequest);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
};
