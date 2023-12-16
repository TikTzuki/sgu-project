import {CartComponent} from './cart.component';
import {CartStatusComponent} from './cart-status/cart-status.component';
import {CartService} from './cart.service';
import {ModuleWithProviders, NgModule} from "@angular/core";
import {SharedModule} from '../shared/shared.module';
import {CheckOutComponent} from './check-out/check-out.component';
import {PaymentsuccessComponent} from './check-out/paymentsuccess/paymentsuccess.component';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';

@NgModule({
  imports: [SharedModule, FontAwesomeModule],
  declarations: [CartComponent, CartStatusComponent, CheckOutComponent, PaymentsuccessComponent],
  providers: [CartService],
  exports: [CartStatusComponent]
})
export class CartModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);
  }

  static forRoot(): ModuleWithProviders<CartModule> {
    return {
      ngModule: CartModule,
      providers: [
        CartService
      ]
    }
  }
}
