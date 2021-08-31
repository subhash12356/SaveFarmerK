package com.example.kalpesh.savefarmer.API;

import android.util.Log;

import com.example.kalpesh.savefarmer.model.AllAdminviewrequest;
import com.example.kalpesh.savefarmer.model.AllCategory;
import com.example.kalpesh.savefarmer.model.AllFarmer;
import com.example.kalpesh.savefarmer.model.AllFarmerTans;
import com.example.kalpesh.savefarmer.model.AllFpayment;
import com.example.kalpesh.savefarmer.model.AllGvscheme;
import com.example.kalpesh.savefarmer.model.AllProduct;
import com.example.kalpesh.savefarmer.model.AllStaff;
import com.example.kalpesh.savefarmer.model.AllState;
import com.example.kalpesh.savefarmer.model.AllStorage;
import com.example.kalpesh.savefarmer.model.AllStoragetypedata;
import com.example.kalpesh.savefarmer.model.AllStoragetypedetail;
import com.example.kalpesh.savefarmer.model.AllWholesellerdata;
import com.example.kalpesh.savefarmer.model.AllWholesellerdetails;
import com.example.kalpesh.savefarmer.model.AllWpayment;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ApiCall {

    public String RegistrationFunction(String name, String gender, String address, String stateid, String city, String email,
                                       String cno, String password, String status) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("name", name)
                .add("gender", gender)
                .add("address", address)
                .add("stateid", stateid)
                .add("city", city)
                .add("email", email)
                .add("cno", cno)
                .add("password", password)
                .add("status", status);

        String url = ApiReso.REGISTRATION;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String AddstoragedetailFunction(String storagename, String address, String stateid, String city, String area,
                                           String capacity, String descri, String cno, String storageid, String rate) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("sname", storagename)
                .add("address", address)
                .add("stateid", stateid)
                .add("city", city)
                .add("area", area)
                .add("capacity", capacity)
                .add("descri", descri)
                .add("cno", cno)
                .add("storageid", storageid)
                .add("rate", rate);

        String url = ApiReso.ADDSTORAGEDETAILS;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String AddstoragedataFunction(String productid, String farmerid, String productquantity, String storageid,
                                         String payablerate, String applyfor, String sellrate, String diff) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("pid", productid)
                .add("fid", farmerid)
                .add("pq", productquantity)
                .add("sid", storageid)
                .add("prate", payablerate)
                .add("applyfor", applyfor)
                .add("diff", diff)
                .add("srate", sellrate);

        String url = ApiReso.ADDSTORAGEDETAIL;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String AddStaffTransFunction(String staffid, String farmerid, String whoid, String dt,
                                        String amt, String asid, String msg, String abid) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("staffid", staffid)
                .add("farmerid", farmerid)
                .add("whoid", whoid)
                .add("dt", dt)
                .add("amt", amt)
                .add("msg", msg)
                .add("abid", abid)
                .add("asid", asid);

        String url = ApiReso.ADDSTAFFTRANS;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String AddadminFunction(String name, String email,
                                   String cno, String password) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("cno", cno)
                .add("password", password);

        String url = ApiReso.ADDADMIN;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String AddfeedbackFunction(String id, String message, String role) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("id", id)
                .add("mess", message)
                .add("role", role);


        String url = ApiReso.ADDFEEDBACK;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String FindEmailFunction(String email, String role) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("role", role)
                .add("email", email);


        String url = ApiReso.FINDEMAIL;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String AddBuyproductFunction(String wholesellerid, String productid, String actualquantity,
                                        String wholesellerquantity, String descri, String farmerid,
                                        String applystorageid, String wanmount, String samount,String newPurchaseRate) {
        String result = "";
        HTTPCall call = new HTTPCall();
        Log.e("samount", samount);
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("wid", wholesellerid)
                .add("pid", productid)
                .add("aq", actualquantity)
                .add("wq", wholesellerquantity)
                .add("descri", descri)
                .add("fid", farmerid)
                .add("wamount", wanmount)
                .add("samount", samount)
                .add("asid", applystorageid)
                .add("newPurchaseRate", newPurchaseRate);

        String url = ApiReso.ADDBUYPRODUCT;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String LoginFunction(String email, String password, String role) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("role", role);

        String url = ApiReso.LOGIN;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String UpdatePasswordFunction(String email, String password, String role) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("role", role);

        String url = ApiReso.UPDATEPASSWORD;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String AddproductFunction(String pname, String pq, String descri, String cid) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("pname", pname)
                .add("pq", pq)
                .add("descri", descri)
                .add("cid", cid);

        String url = ApiReso.ADDPRODUCT;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String UpdaterequestlistFunction(String applybuyid) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("abid", applybuyid);

        String url = ApiReso.UPDATEADMINREQUSTLIST;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String AddstateFunction(String sname) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("sname", sname);

        String url = ApiReso.ADDSTATE;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String AddstoragetypeFunction(String stype) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("stype", stype);

        String url = ApiReso.ADDSTORAGETYPE;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public String AddcatageryFunction(String cname) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("cname", cname);

        String url = ApiReso.ADDCATAGERY;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }


    public AllState ShowAllStatefunction() {
        AllState result = new AllState();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLSTATE;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllState.class);
        return result;
    }

    public AllCategory ShowAllCatagoryfunction() {
        AllCategory result = new AllCategory();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.ALLCATAGORY;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllCategory.class);
        return result;
    }

    public AllStoragetypedetail ShowAllStoragetypedetailsfunction() {
        AllStoragetypedetail result = new AllStoragetypedetail();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLSTORAGETYPE;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllStoragetypedetail.class);
        return result;
    }

    public AllStoragetypedata ShowAllStoragetypedatafunction() {
        AllStoragetypedata result = new AllStoragetypedata();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLSTORAGEDETAILS;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllStoragetypedata.class);
        return result;
    }

    public AllWholesellerdetails ShowAllWholesellerdatafunction() {
        AllWholesellerdetails result = new AllWholesellerdetails();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLWHOLESELLERDETAILS;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllWholesellerdetails.class);
        return result;
    }

    public AllWholesellerdetails GetWholesellerIdfunction(String email) {
        AllWholesellerdetails result = new AllWholesellerdetails();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETWHOLESELLERID + "?email=" + email.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllWholesellerdetails.class);
        return result;
    }

    public AllProduct ShowAllProductdatafunction() {
        AllProduct result = new AllProduct();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLPRODUCT;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllProduct.class);
        return result;
    }

    public AllFarmer ShowFarmerIdfunction(String email) {
        AllFarmer result = new AllFarmer();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETFARMERID + "?email=" + email.trim();
        String output = call.GET(url);
        Log.d("output:", output);
        result = new Gson().fromJson(output, AllFarmer.class);
        return result;
    }

    public AllWholesellerdata ShowAllWholesellerdatafordisplayfunction() {
        AllWholesellerdata result = new AllWholesellerdata();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLSELLERLISTFORWHOLESELLER;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllWholesellerdata.class);
        return result;
    }

    public AllAdminviewrequest ShowAllAdminrequestfunction() {
        AllAdminviewrequest result = new AllAdminviewrequest();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETALLREQUESTLISTFORADMIN;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllAdminviewrequest.class);
        return result;
    }

    public AllWpayment GetWAmountfunction(String id) {
        AllWpayment result = new AllWpayment();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETWHOLESELLERAMOUNT + "?id=" + id.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllWpayment.class);
        return result;
    }

    public AllFpayment GetFAmountfunction(String id) {
        AllFpayment result = new AllFpayment();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETFARMERAMOUNT + "?id=" + id.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllFpayment.class);
        return result;
    }

    public AllFarmerTans GetFarmertransdetailfunction(String id) {
        Log.e("fid", id);
        AllFarmerTans result = new AllFarmerTans();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETFARMERTRANS + "?fid=" + id.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllFarmerTans.class);
        return result;
    }

    public AllStaff StaffLoginFunction(String email, String pwd) {
        AllStaff result = new AllStaff();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.STAFFLOGIN + "?email=" + email.trim() + "&pwd=" + pwd.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllStaff.class);
        return result;
    }

    public AllStorage StorageDetailForStaffFunction(String sid) {
        AllStorage result = new AllStorage();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETSTORAGEDETAILFORSTAFF + "?sid=" + sid.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllStorage.class);
        return result;
    }

    public AllFarmerTans GetWholesellertransdetailfunction(String id) {
        AllFarmerTans result = new AllFarmerTans();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETWHOLESELLERTRANS + "?fid=" + id.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllFarmerTans.class);
        return result;
    }

    public AllFarmerTans GetFarmerstoragetransdetailfunction(String id) {
        AllFarmerTans result = new AllFarmerTans();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETFARMERSTORAGETRANS + "?fid=" + id.trim();
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllFarmerTans.class);
        return result;
    }

    public AllGvscheme GetGvschemefunction() {
        AllGvscheme result = new AllGvscheme();
        HTTPCall call = new HTTPCall();
        String url = ApiReso.GETGOVERNMENTSCHEMES;
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllGvscheme.class);
        return result;
    }

    public String Updateamountfunction(String uid, String amount) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("uid", uid)
                .add("amount", amount);


        String url = ApiReso.UPDATEAMOUNT;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("Result", result);
        return result;
    }

    public String FUpdateamountfunction(String uid, String amount) {
        String result = "";
        HTTPCall call = new HTTPCall();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("uid", uid)
                .add("amount", amount);


        String url = ApiReso.FUPDATEAMOUNT;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("Result", result);
        return result;
    }
}
