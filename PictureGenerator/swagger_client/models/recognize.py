# coding: utf-8

"""
    Betaface API 2.0

    Betaface face recognition API.  # noqa: E501

    OpenAPI spec version: 2.0
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


import pprint
import re  # noqa: F401

import six

from swagger_client.models.recognize_result import RecognizeResult  # noqa: F401,E501


class Recognize(object):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    swagger_types = {
        'recognize_uuid': 'str',
        'results': 'list[RecognizeResult]'
    }

    attribute_map = {
        'recognize_uuid': 'recognize_uuid',
        'results': 'results'
    }

    def __init__(self, recognize_uuid=None, results=None):  # noqa: E501
        """Recognize - a model defined in Swagger"""  # noqa: E501

        self._recognize_uuid = None
        self._results = None
        self.discriminator = None

        if recognize_uuid is not None:
            self.recognize_uuid = recognize_uuid
        if results is not None:
            self.results = results

    @property
    def recognize_uuid(self):
        """Gets the recognize_uuid of this Recognize.  # noqa: E501

        recognize request unique identifier.  # noqa: E501

        :return: The recognize_uuid of this Recognize.  # noqa: E501
        :rtype: str
        """
        return self._recognize_uuid

    @recognize_uuid.setter
    def recognize_uuid(self, recognize_uuid):
        """Sets the recognize_uuid of this Recognize.

        recognize request unique identifier.  # noqa: E501

        :param recognize_uuid: The recognize_uuid of this Recognize.  # noqa: E501
        :type: str
        """

        self._recognize_uuid = recognize_uuid

    @property
    def results(self):
        """Gets the results of this Recognize.  # noqa: E501

        collection of recognize results.  # noqa: E501

        :return: The results of this Recognize.  # noqa: E501
        :rtype: list[RecognizeResult]
        """
        return self._results

    @results.setter
    def results(self, results):
        """Sets the results of this Recognize.

        collection of recognize results.  # noqa: E501

        :param results: The results of this Recognize.  # noqa: E501
        :type: list[RecognizeResult]
        """

        self._results = results

    def to_dict(self):
        """Returns the model properties as a dict"""
        result = {}

        for attr, _ in six.iteritems(self.swagger_types):
            value = getattr(self, attr)
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: x.to_dict() if hasattr(x, "to_dict") else x,
                    value
                ))
            elif hasattr(value, "to_dict"):
                result[attr] = value.to_dict()
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], item[1].to_dict())
                    if hasattr(item[1], "to_dict") else item,
                    value.items()
                ))
            else:
                result[attr] = value
        if issubclass(Recognize, dict):
            for key, value in self.items():
                result[key] = value

        return result

    def to_str(self):
        """Returns the string representation of the model"""
        return pprint.pformat(self.to_dict())

    def __repr__(self):
        """For `print` and `pprint`"""
        return self.to_str()

    def __eq__(self, other):
        """Returns true if both objects are equal"""
        if not isinstance(other, Recognize):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other