package gvoltr.kmptest.view.screens.home

import DefaultSpacer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gvoltr.kmptest.data.model.profile.WalletNft
import gvoltr.kmptest.view.compose.EditTextFilled
import gvoltr.kmptest.view.compose.GuerrillaText
import gvoltr.kmptest.view.compose.theme.AppColor
import gvoltr.kmptest.view.screens.composeShowcase.ComposeShowcaseScreen
import gvoltr.kmptest.view.screens.generateWallet.GenerateWalletScreen
import gvoltr.kmptest.view.viewArch.collectAsState
import org.koin.compose.viewmodel.koinViewModel


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeViewModel>()

        ScreenContent(viewModel)
    }
}

@Composable
private fun ScreenContent(
    viewModel: HomeViewModel
) {
    val state = viewModel.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow

    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Button(onClick = { navigator.push(ComposeShowcaseScreen()) }) {
                GuerrillaText("View Examples")
            }
            DefaultSpacer(16.dp)
            Button(onClick = { navigator.push(GenerateWalletScreen()) }) {
                GuerrillaText("Generate Wallet")
            }
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            EditTextFilled(
                modifier = Modifier.weight(1f),
                text = state.walletAddress,
                hint = "Wallet Address",
                placeholder = "0xabc...",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onTextChanged = { viewModel.sendAction(HomeUserAction.UserEnteredAddress(it)) }
            )
            DefaultSpacer(8.dp)
            Column(Modifier.width(94.dp)) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.sendAction(HomeUserAction.SearchNfts) }
                ) {
                    GuerrillaText("Search")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.sendAction(HomeUserAction.ClearSearch) }
                ) {
                    GuerrillaText("Clear")
                }
            }
        }

        when {
            state.walletAddress.isEmpty() -> {
                SearchHistory(
                    wallets = state.walletsHistory,
                    onWalletSelected = {
                        viewModel.sendAction(HomeUserAction.UserEnteredAddress(it))
                        viewModel.sendAction(HomeUserAction.SearchNfts)
                    }
                )
            }

            state.loading -> {
                DefaultSpacer(16.dp)
                CircularProgressIndicator()
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    items(state.listItems, key = { it.key }) { item ->
                        ListItem(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.SearchHistory(
    wallets: List<String>,
    onWalletSelected: (String) -> Unit
) {
    Column (
        modifier = Modifier.weight(1f)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ){
        wallets.forEach { wallet ->
            GuerrillaText(
                modifier = Modifier.clickable { onWalletSelected(wallet) },
                color = AppColor.Primary,
                text = wallet
            )
        }
    }
}

@Composable
private fun LazyItemScope.ListItem(
    item: HomeState.ListItem,
) {
    when (item) {
        is HomeState.ListItem.CollectionHeader -> CollectionHeader(header = item)
        is HomeState.ListItem.NftsRow -> DomainsRow(
            item = item
        )
    }
}

@Suppress("MagicNumber")
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.CollectionHeader(
    header: HomeState.ListItem.CollectionHeader,
) = Row(
    modifier = Modifier
        .animateItemPlacement()
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .padding(start = 16.dp, end = 8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    header.image?.let {
//        RemoteImage(
//            modifier = Modifier
//                .size(24.dp)
//                .clip(CircleShape),
//            url = it,
//        )
        DefaultSpacer(size = 8.dp)
    }
    Text(
        modifier = Modifier.weight(1f),
        text = header.name,
        color = AppColor.Secondary,
        fontWeight = FontWeight.W500,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.DomainsRow(
    item: HomeState.ListItem.NftsRow
) = Row(
    modifier = Modifier
        .animateItemPlacement()
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
) {
    item.nfts.firstOrNull()?.let { domain ->
        Nft(domain) { }
    }
    DefaultSpacer(size = 16.dp)
    if (item.nfts.size > 1) {
        item.nfts.lastOrNull()?.let { domain ->
            Nft(domain) { }
        }
    } else {
        Box(Modifier.weight(1f))
    }
}

@Suppress("MagicNumber")
@Composable
private fun RowScope.Nft(
    nft: WalletNft,
    onClick: () -> Unit
) = Box(
    modifier = Modifier
        .weight(1f)
        .aspectRatio(1f)
        .clip(RoundedCornerShape(12.dp))
        .background(AppColor.Primary)
        .clickable(onClick = onClick)
) {

    GuerrillaText(
        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
        color = Color.White,
        text = nft.name,
    )
//    RemoteImage(
//        modifier = Modifier.fillMaxSize(),
//        url = nft.imageUrl,
//    )
}