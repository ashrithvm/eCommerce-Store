import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountsComponent } from './accounts/accounts.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule, provideHttpClient, withFetch} from "@angular/common/http";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatError, MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatTab, MatTabGroup, MatTabLabel} from "@angular/material/tabs";
import {MatAnchor, MatButton, MatFabButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {
  MatCard, MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardImage,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import { AdminPageComponent } from './admin-page/admin-page.component';
import {RouterModule, Routes} from "@angular/router";
import { PuzzlePageComponent } from './puzzle-page/puzzle-page.component';
import {adminGuard, cartGuard, loginGuard, productGuard} from "./account-guard.guard";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import { PuzzleEditFormComponent } from './puzzle-edit-form/puzzle-edit-form.component';
import {
  MatList,
  MatListItem,
  MatListItemAvatar,
  MatListItemIcon,
  MatListItemLine,
  MatListItemTitle, MatNavList
} from "@angular/material/list";
import {MatIcon} from "@angular/material/icon";
import { ProductDetailsComponent } from './product-details/product-details.component';
import {NgOptimizedImage} from "@angular/common";
import { CartPageComponent } from './cart-page/cart-page.component';
import {MatLine, MatOption} from "@angular/material/core";
import {MatDivider} from "@angular/material/divider";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {ModalModule} from "ngx-bootstrap/modal";
import {TypeaheadModule} from "ngx-bootstrap/typeahead";
import {MatToolbar} from "@angular/material/toolbar";
import {MatSidenavContainer, MatSidenavContent, MatSidenavModule} from "@angular/material/sidenav";
import { NavigationListComponent } from './navigation-list/navigation-list.component';
import {AccountInfoComponent} from "./account-info/account-info.component";
import {MatSelect} from "@angular/material/select";
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { OrdersComponent } from './orders/orders.component';
import {
  MatExpansionModule,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from "@angular/material/expansion";
import { OwnedPuzzlesComponent } from './owned-puzzles/owned-puzzles.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {CustomPuzzleFormComponent} from "./custom-puzzle-form/custom-puzzle-form.component";
const appRoutes: Routes = [
  {path: 'login', component: AccountsComponent, canActivate: [loginGuard]},
  {path: 'admin', component: AdminPageComponent, canActivate: [adminGuard]},
  {path: 'puzzles/details', component: ProductDetailsComponent, canActivate: [adminGuard]},
  {path: 'puzzles', component: PuzzlePageComponent, canActivate: [productGuard]},
  {path: 'puzzles/info', component: ProductDetailsComponent, canActivate: [productGuard], data: {title: 'Product Details'}},
  {path: 'puzzle-edit-form/:id', component: PuzzleEditFormComponent, canActivate: [adminGuard]},
  {path: 'puzzle-create-form', component: PuzzleEditFormComponent, canActivate: [adminGuard]},
  {path: 'custom-puzzle-form', component: CustomPuzzleFormComponent, canActivate: [productGuard]},
  {path: 'cart', component: CartPageComponent, canActivate: [productGuard, cartGuard]},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'checkout', component: CheckoutPageComponent, canActivate: [productGuard, cartGuard]},
  {path: 'my-puzzle', component: OwnedPuzzlesComponent, canActivate: [productGuard, cartGuard]},
  {path: 'order-history', component:OrderHistoryComponent, canActivate: [productGuard, cartGuard]},
  {path: 'orders', component: OrdersComponent, canActivate: [adminGuard]}
  ];
@NgModule({
  declarations: [
    AppComponent,
    AccountsComponent,
    AdminPageComponent,
    AccountInfoComponent,
    PuzzlePageComponent,
    PuzzleEditFormComponent,
    ProductDetailsComponent,
    CartPageComponent,
    NavigationListComponent,
    CheckoutPageComponent,
    OrdersComponent,
    OwnedPuzzlesComponent,
    OrderHistoryComponent,
    CustomPuzzleFormComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatFormField,
        MatFormFieldModule,
        MatInput,
        MatTabLabel,
        MatButton,
        MatCardContent,
        MatCard,
        MatError,
        MatCardTitle,
        RouterModule.forRoot(appRoutes),
        MatMenuTrigger,
        MatMenu,
        MatMenuItem,
        MatCardHeader,
        MatList,
        MatListItem,
        MatFabButton,
        MatIcon,
        MatMiniFabButton,
        NgOptimizedImage,
        MatCardImage,
        MatCardSubtitle,
        MatCardActions,
        MatIconButton,
        MatLine,
        MatListItemTitle,
        MatListItemLine,
        MatDivider,
        MatListItemIcon,
        MatListItemAvatar,
        NgbModule,
        FormsModule,
        ModalModule.forRoot(),
        MatAnchor,
        TypeaheadModule.forRoot(),
        MatToolbar,
        MatSidenavContainer,
        MatNavList,
        MatSidenavModule,
        MatSidenavContent,
        MatSelect,
        MatOption,
        MatTabGroup,
        MatTab,
        MatExpansionPanel,
        MatExpansionPanelHeader,
        MatExpansionPanelTitle,
        MatExpansionModule,
        MatSlideToggle
    ],
  providers: [
    provideHttpClient(withFetch()),
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
