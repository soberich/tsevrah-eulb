import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MasterAccount from './account/master-account';
import MonetaryTransaction from './transaction/monetary-transaction';
import AccountState from './transaction/account-state';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}master-account`} component={MasterAccount} />
      <ErrorBoundaryRoute path={`${match.url}monetary-transaction`} component={MonetaryTransaction} />
      <ErrorBoundaryRoute path={`${match.url}account-state`} component={AccountState} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
