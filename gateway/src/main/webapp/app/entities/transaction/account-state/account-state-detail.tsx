import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './account-state.reducer';
import { IAccountState } from 'app/shared/model/transaction/account-state.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountStateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AccountStateDetail = (props: IAccountStateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { accountStateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gatewayApp.transactionAccountState.detail.title">AccountState</Translate> [<b>{accountStateEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="customerID">
              <Translate contentKey="gatewayApp.transactionAccountState.customerID">Customer ID</Translate>
            </span>
          </dt>
          <dd>{accountStateEntity.customerID}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="gatewayApp.transactionAccountState.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{accountStateEntity.balance}</dd>
        </dl>
        <Button tag={Link} to="/account-state" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/account-state/${accountStateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ accountState }: IRootState) => ({
  accountStateEntity: accountState.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AccountStateDetail);
