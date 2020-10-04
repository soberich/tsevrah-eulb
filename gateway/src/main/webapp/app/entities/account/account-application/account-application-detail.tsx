import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './account-application.reducer';
import { IAccountApplication } from 'app/shared/model/account/account-application.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountApplicationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AccountApplicationDetail = (props: IAccountApplicationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { accountApplicationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gatewayApp.accountAccountApplication.detail.title">AccountApplication</Translate> [
          <b>{accountApplicationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="customerID">
              <Translate contentKey="gatewayApp.accountAccountApplication.customerID">Customer ID</Translate>
            </span>
          </dt>
          <dd>{accountApplicationEntity.customerID}</dd>
          <dt>
            <span id="initialCredit">
              <Translate contentKey="gatewayApp.accountAccountApplication.initialCredit">Initial Credit</Translate>
            </span>
          </dt>
          <dd>{accountApplicationEntity.initialCredit}</dd>
        </dl>
        <Button tag={Link} to="/account-application" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/account-application/${accountApplicationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ accountApplication }: IRootState) => ({
  accountApplicationEntity: accountApplication.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AccountApplicationDetail);
