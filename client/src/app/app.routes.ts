import {Routes, RouterModule} from '@angular/router';
import {Home} from './home';
import {RadarChartDemo} from "./chart/radarchartdemo.component";
import {NoContent} from './no-content';

import {DataResolver} from './app.resolver';


export const ROUTES: Routes = [
  {path: '', component: Home},
  {path: 'home', component: Home},
  {path: 'chart', component: RadarChartDemo},
  {path: '**', component: NoContent},
];
