package com.example.chefmate.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.chefmate.R
import com.example.chefmate.database.entity.TagEntity

@Composable
fun CustomDiaLogEditIngredient(
    title: String,
    onDismiss: () -> Unit,
    ingredientName: String,
    onNameChange: (String) -> Unit,
    ingredientWeight: String,
    onWeightChange: (String) -> Unit,
    ingredientUnit: String,
    onUnitChange: (String) -> Unit,
    confirmText: String,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = {}, content = {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(Color(0xFFFFFFFF))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFB923C))
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = title,
                    color = Color(0xFFFFFFFF),
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "close dialog",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier.clickable { onDismiss() })
            }
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Label("Tên nguyên liệu")
                EditTextWithouthDescripe(
                    value = ingredientName, onValueChange = onNameChange, label = ""
                )
                Label(text = "Định lượng", modifier = Modifier.padding(top = 10.dp))
                Row {
                    EditTextWithouthDescripe(
                        value = ingredientWeight,
                        onValueChange = onWeightChange,
                        label = "",
                        modifier = Modifier.weight(1f)
                    )
                    EditTextWithouthDescripe(
                        value = ingredientUnit,
                        onValueChange = onUnitChange,
                        label = "",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    )
                }
                Button(
                    onClick = { onConfirm() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF97518)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = confirmText
                    )
                }
            }
        }
    })
}

@Composable
fun CustomAlertDialog(
    title: String, content: String, onDismiss: () -> Unit, confirmText: String, onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(

        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(0xFFFFFFFF))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFFB923C))
                        .fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "close dialog",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .padding(16.dp)
                    )
                }
                Text(
                    text = content,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
                )
                Button(
                    onClick = { onConfirm() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF97518)
                    ),
                    modifier = Modifier
                        .padding(end = 20.dp, bottom = 20.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = confirmText
                    )
                }
            }
        }
    )
}

@Composable
fun AddTagDialog(tag: String, onTagChange: (String) -> Unit ,onDismiss: () -> Unit, listTags: List<String>, onAddTag: () -> Unit) {
    Dialog(
        onDismissRequest = {},
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                val (contentRef, closeRef) = createRefs()
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color(0xFFFFFFFF))
                        .padding(20.dp)
                        .constrainAs(contentRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = "Thêm tag",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    EditTextWithouthDescripe(
                        value = tag,
                        onValueChange = onTagChange,
                        label = "Tên tag"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (listTags.isNotEmpty()) {
                            listTags.forEach { tag ->
                                Text(
                                    text = tag,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .background(Color(0xFFFFC107), shape = RoundedCornerShape(3.dp))
                                        .padding(2.dp)

                                )
                            }
                        } else {
                            Text(
                                text = "Chưa thêm tag nào",
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                            )
                        }
                    }
                    Button(
                        onClick = onAddTag,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF97518)
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = "Thêm",
                            fontSize = 14.sp,
                        )
                    }
                }
                Button(
                    onClick = onDismiss,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(closeRef) {
                            top.linkTo(contentRef.bottom, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "close dialog",
                        tint = Color(0xFFF97518),
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun DialogPreview() {
//    CustomDiaLogEditIngredient("",{}, "", {}, "", {}, "", {}, "", {})
//    CustomAlertDialog(title = "Title", content = "Content", onDismiss = {}, confirmText = "Xóa", onConfirm = {})
    AddTagDialog("", {}, {}, listOf(), {})
}