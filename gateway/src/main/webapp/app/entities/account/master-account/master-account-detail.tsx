import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './master-account.reducer';
import { IMasterAccount } from 'app/shared/model/account/master-account.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMasterAccountDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MasterAccountDetail = (props: IMasterAccountDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { masterAccountEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gatewayApp.accountMasterAccount.detail.title">MasterAccount</Translate> [<b>{masterAccountEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="customerID">
              <Translate contentKey="gatewayApp.accountMasterAccount.customerID">Customer ID</Translate>
            </span>
          </dt>
          <dd>{masterAccountEntity.customerID}</dd>
          <dt>
            <span id="initialCredit">
              <Translate contentKey="gatewayApp.accountMasterAccount.initialCredit">Initial Credit</Translate>
            </span>
          </dt>
          <dd>{masterAccountEntity.initialCredit}</dd>
        </dl>
        <Button tag={Link} to="/master-account" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/master-account/${masterAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ masterAccount }: IRootState) => ({
  masterAccountEntity: masterAccount.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MasterAccountDetail);
