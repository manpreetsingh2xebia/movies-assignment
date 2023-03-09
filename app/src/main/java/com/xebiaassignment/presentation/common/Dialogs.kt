package com.xebiaassignment.presentation.common

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xebiaassignment.presentation.ui.theme.XebiaAssignmentTheme
import com.xebiaassignment.R

@Composable
fun InternetConnectionDialog(){
    XebiaAssignmentTheme() {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            buttons = {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.please_check_yout_internet_connection),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = dimensionResource(id = R.dimen.txt_size_18).value.sp
                        ),
                        fontWeight = FontWeight.Medium,

                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.size_10dp),
                                top = dimensionResource(id = R.dimen.size_10dp)
                            )
                    )

                }
            }
        )
    }
}