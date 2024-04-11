import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {AccountService} from "./account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnDestroy{
  title = 'puzzles';

  mobileQuery: MediaQueryList;
  private readonly _mobileQueryListener: () => void;
  protected localStorage?: Storage
  constructor(changeDetectorRef: ChangeDetectorRef,
              media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
    try{
      this.localStorage = localStorage
    }
    catch(e){
      console.log("Local storage is not available")
      this.localStorage = undefined
    }
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

}
