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


class Transform(object):
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
        'transform_uuid': 'str',
        'image_base64': 'str'
    }

    attribute_map = {
        'transform_uuid': 'transform_uuid',
        'image_base64': 'image_base64'
    }

    def __init__(self, transform_uuid=None, image_base64=None):  # noqa: E501
        """Transform - a model defined in Swagger"""  # noqa: E501

        self._transform_uuid = None
        self._image_base64 = None
        self.discriminator = None

        if transform_uuid is not None:
            self.transform_uuid = transform_uuid
        if image_base64 is not None:
            self.image_base64 = image_base64

    @property
    def transform_uuid(self):
        """Gets the transform_uuid of this Transform.  # noqa: E501

        transform request unique identifier.  # noqa: E501

        :return: The transform_uuid of this Transform.  # noqa: E501
        :rtype: str
        """
        return self._transform_uuid

    @transform_uuid.setter
    def transform_uuid(self, transform_uuid):
        """Sets the transform_uuid of this Transform.

        transform request unique identifier.  # noqa: E501

        :param transform_uuid: The transform_uuid of this Transform.  # noqa: E501
        :type: str
        """

        self._transform_uuid = transform_uuid

    @property
    def image_base64(self):
        """Gets the image_base64 of this Transform.  # noqa: E501

        base64 encoded transofrm result image file (jpg or png).  # noqa: E501

        :return: The image_base64 of this Transform.  # noqa: E501
        :rtype: str
        """
        return self._image_base64

    @image_base64.setter
    def image_base64(self, image_base64):
        """Sets the image_base64 of this Transform.

        base64 encoded transofrm result image file (jpg or png).  # noqa: E501

        :param image_base64: The image_base64 of this Transform.  # noqa: E501
        :type: str
        """

        self._image_base64 = image_base64

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
        if issubclass(Transform, dict):
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
        if not isinstance(other, Transform):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other