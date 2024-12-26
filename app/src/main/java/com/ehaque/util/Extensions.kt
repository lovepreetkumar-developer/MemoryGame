package com.ehaque.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    result?.let {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
        return result
    }

    return ""
}

fun createMultiPart(path: String, key: String): MultipartBody.Part {
    val file = File(path)
    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
    return MultipartBody.Part.createFormData(key, file.name, requestFile)
}


fun getCurrentPageHeader(currentPage: Int): SpannableString {
    val s = "$currentPage/3"
    val ss1 = SpannableString(s)
    ss1.setSpan(RelativeSizeSpan(2f), 0, 1, 0)
    return ss1
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidMobile(phone: String): Boolean {
    return Patterns.PHONE.matcher(phone).matches()
}

@Throws(IOException::class)
fun getBytes(uri: Uri?, context: Context): ByteArray? {
    val inputStream = context.contentResolver.openInputStream(uri!!)
    val byteBuffer = ByteArrayOutputStream()
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)
    var len: Int
    while (inputStream!!.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }
    return byteBuffer.toByteArray()
}

/*----------------------------Dates functions--------------------------*/

var commanDateTimeFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())


@Throws(ParseException::class)
fun getNextDate(string: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(string)
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    return format.format(calendar.time)
}

fun getDateFromString(string: String): Date? {
    /*2017-11-28 18:18:04*/
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    var date: Date? = null
    try {
        date = simpleDateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    } finally {
        return date
    }
}

fun getCurrentDate(): String {
    /*2017-11-28 18:18:04*/
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Calendar.getInstance().time
    return simpleDateFormat.format(date)
}

fun getCurrentDate(calendar: Calendar): String? {
    /*2017-11-28 18:18:04*/
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = calendar.time
    return simpleDateFormat.format(date)
}

fun getMonthForInt(num: Int): String? {
    var month = "wrong"
    val dfs = DateFormatSymbols()
    val months = dfs.shortMonths
    if (num >= 0 && num <= 11) {
        month = months[num]
    }
    return month
}

// getting only month from full date
fun getOnlyMonth(date: Int): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date * 1000.toLong()
    return SimpleDateFormat("MMM").format(calendar.time)
}

fun getOnlyMonth(date: Calendar): String? {
    return SimpleDateFormat("MMM").format(date.time)
}

// getting only date from full date
fun getOnlyDate(date: Int): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date * 1000.toLong()
    return calendar[Calendar.DAY_OF_MONTH].toString()
}

fun getDateFromStringDate(date: String?): String? {
    val dateFormatOne = SimpleDateFormat("dd-MMM-yyyy")
    val dateFormatTwo = SimpleDateFormat("yyyy-MM-dd")
    var date1: Date? = null
    try {
        date1 = dateFormatOne.parse(date)
    } catch (ex: ParseException) {
        ex.printStackTrace()
    }
    return dateFormatTwo.format(date1)
}


/*----------------------------String functions--------------------------*/


/*----------------------------String functions--------------------------*/

fun getbitmap(arr: ByteArray?): Bitmap? {
    return BitmapFactory.decodeStream(ByteArrayInputStream(arr))
}

fun checkText(text: String?): Boolean {
    return text != null && !text.equals("", ignoreCase = true)
}

/*----------------------------Bitmap functions--------------------------*/

/*----------------------------Bitmap functions--------------------------*/
fun getCircleBitmap(bit: Bitmap?): Bitmap? {
    val bitmap1 = Bitmap.createScaledBitmap(bit!!, 100, 100, true)
    val output = Bitmap.createBitmap(
        bitmap1.width,
        bitmap1.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(output)
    val color = Color.RED
    val paint = Paint()
    val rect = Rect(0, 0, bitmap1.width, bitmap1.height)
    val rectF = RectF(rect)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap1, rect, rect, paint)
    bitmap1.recycle()
    return output
}

fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
            matrix.setRotate(180f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_TRANSPOSE -> {
            matrix.setRotate(90f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
        ExifInterface.ORIENTATION_TRANSVERSE -> {
            matrix.setRotate(-90f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
        else -> return bitmap
    }
    return try {
        val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        bmRotated
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        null
    }
}

fun getCompressedBitmap(bitmap: Bitmap): Bitmap? {
    try {
        return Bitmap.createScaledBitmap(bitmap, bitmap.width / 3, bitmap.height / 3, true)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return null
}

fun compressImage(bitmapImage: Bitmap): ByteArray? {
    val byteImage: ByteArray
    val stream = ByteArrayOutputStream()
    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
    byteImage = stream.toByteArray()
    return byteImage
}

fun dpToPx(dp: Float): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return dp * scale + 0.5f
}

/*//  validation to check phonenumber
fun validatePhoneNumber(phoneNo: String): Boolean {
    //validate phone numbers of format "1234567890"
    return if (phoneNo.matches("\\d{10}")) true else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) true else  //return false if nothing matches the input
        if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) true else phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")
}*/

// hide keyboard
private var inputMethodManager: InputMethodManager? = null

fun hideSoftKeyboard(activity: Activity) {
    if (inputMethodManager == null) inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (activity.currentFocus != null) inputMethodManager!!.hideSoftInputFromWindow(
        activity.currentFocus!!.windowToken,
        0
    )
}

fun showSoftKeyboard(activity: Activity) {
    if (inputMethodManager == null) inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (activity.currentFocus != null) inputMethodManager!!.showSoftInput(activity.currentFocus, 0)
}

// Make a phone call
fun dialPhoneNumber(context: Context, phoneNumber: String?) {
    if (phoneNumber != null && !TextUtils.isEmpty(phoneNumber)) {
        val number: String = phoneNumber
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}

// Send a SMS
fun composeSmsMessage(context: Context, message: String?, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber"))
    intent.putExtra("sms_body", message)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

/*fun getHeaderMap(userModel: UserModel): HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    hashMap[Constants.KEY_USER_ID] = userModel.user_id
    hashMap[Constants.KEY_SESSION] = userModel.session_key
    return hashMap
}*/

fun gettimecounting(sobertime: String): String? {
    val stringBuilder = StringBuilder()
    try {
        val seconds = Calendar.getInstance().timeInMillis / 1000 - sobertime.toLong()
        val year = seconds / (3600 * 24 * 365)
        val month = seconds % (3600 * 24 * 365) / (30 * 24 * 3600)
        val days = seconds % (3600 * 24 * 365) % (30 * 24 * 3600) / (3600 * 24)
        if (year > 0) {
            stringBuilder.append(year.toString() + " year" + if (year > 1) "s " else " ")
        } else if (month > 0) {
            stringBuilder.append(month.toString() + " month" + if (month > 1) "s " else " ")
        }
        if (days > 0) {
            stringBuilder.append(days.toString() + " day" + if (days > 1) "s " else " ")
        }
    } catch (e: java.lang.Exception) {
    } finally {
        return stringBuilder.toString()
    }
}

fun gettcoincounting(sobertime: String): Array<String?>? {
    val s = arrayOfNulls<String>(2)
    try {
        val seconds = Calendar.getInstance().timeInMillis / 1000 - sobertime.toLong()
        val year = seconds / (3600 * 24 * 365)
        if (year > 0) {
            s[0] = year.toString() + ""
            s[1] = "year" + if (year > 1) "s" else ""
        } else {
            val sd = seconds % (3600 * 24 * 365) / (24 * 3600)
            s[0] = sd.toString() + ""
            s[1] = "day" + if (sd > 1) "s" else ""
        }
    } catch (e: java.lang.Exception) {
    } finally {
        return s
    }
}

fun getTimeZone(): String? {
    return Calendar.getInstance().timeZone.id
}

fun getTimeStamp(): String? {
    return (Calendar.getInstance().timeInMillis / 1000).toString()
}

fun getUtcTimeStamp(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    return calendar.timeInMillis / 1000
}

fun getCalenderObject(epoch: Long): Calendar? {
    val c = Calendar.getInstance()
    c.timeInMillis = epoch * 1000
    return c
}

fun getCalenderObject(epoch: String): Calendar? {
    return getCalenderObject(epoch.toLong())
}

/* public static Calendar getCalenderInChat(long epoch) {
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
         cal.setTimeInMillis(epoch);
         cal.setTimeZone(TimeZone.getDefault());
        return cal;

     }*/
fun getTimeStamp(calendar: Calendar): String? {
    return (calendar.timeInMillis / 1000).toString()
}

private val dateFormanew = SimpleDateFormat("dd MMM yyyy")
private val timeformat = SimpleDateFormat("hh:mm a")

fun getMessageId(): String? {
    return System.currentTimeMillis().toString()
}

fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}

@Throws(java.lang.Exception::class)
fun deleteDir(dir: File?): Boolean {
    if (dir != null && dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir!!.delete()
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

