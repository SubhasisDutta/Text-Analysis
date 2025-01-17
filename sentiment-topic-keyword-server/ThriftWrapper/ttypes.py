#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException

from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class SentiRequestObject:
  """
  Attributes:
   - mainText
   - textType
   - title
   - middleParas
   - lastPara
   - topDomain
   - subDomain
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'mainText', None, None, ), # 1
    (2, TType.STRING, 'textType', None, "microblogs", ), # 2
    (3, TType.STRING, 'title', None, "", ), # 3
    (4, TType.STRING, 'middleParas', None, "", ), # 4
    (5, TType.STRING, 'lastPara', None, "", ), # 5
    (6, TType.STRING, 'topDomain', None, "", ), # 6
    (7, TType.STRING, 'subDomain', None, "", ), # 7
  )

  def __init__(self, mainText=None, textType=thrift_spec[2][4], title=thrift_spec[3][4], middleParas=thrift_spec[4][4], lastPara=thrift_spec[5][4], topDomain=thrift_spec[6][4], subDomain=thrift_spec[7][4],):
    self.mainText = mainText
    self.textType = textType
    self.title = title
    self.middleParas = middleParas
    self.lastPara = lastPara
    self.topDomain = topDomain
    self.subDomain = subDomain

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.mainText = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.STRING:
          self.textType = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.STRING:
          self.title = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.STRING:
          self.middleParas = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.STRING:
          self.lastPara = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.STRING:
          self.topDomain = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 7:
        if ftype == TType.STRING:
          self.subDomain = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('SentiRequestObject')
    if self.mainText is not None:
      oprot.writeFieldBegin('mainText', TType.STRING, 1)
      oprot.writeString(self.mainText)
      oprot.writeFieldEnd()
    if self.textType is not None:
      oprot.writeFieldBegin('textType', TType.STRING, 2)
      oprot.writeString(self.textType)
      oprot.writeFieldEnd()
    if self.title is not None:
      oprot.writeFieldBegin('title', TType.STRING, 3)
      oprot.writeString(self.title)
      oprot.writeFieldEnd()
    if self.middleParas is not None:
      oprot.writeFieldBegin('middleParas', TType.STRING, 4)
      oprot.writeString(self.middleParas)
      oprot.writeFieldEnd()
    if self.lastPara is not None:
      oprot.writeFieldBegin('lastPara', TType.STRING, 5)
      oprot.writeString(self.lastPara)
      oprot.writeFieldEnd()
    if self.topDomain is not None:
      oprot.writeFieldBegin('topDomain', TType.STRING, 6)
      oprot.writeString(self.topDomain)
      oprot.writeFieldEnd()
    if self.subDomain is not None:
      oprot.writeFieldBegin('subDomain', TType.STRING, 7)
      oprot.writeString(self.subDomain)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    if self.mainText is None:
      raise TProtocol.TProtocolException(message='Required field mainText is unset!')
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.mainText)
    value = (value * 31) ^ hash(self.textType)
    value = (value * 31) ^ hash(self.title)
    value = (value * 31) ^ hash(self.middleParas)
    value = (value * 31) ^ hash(self.lastPara)
    value = (value * 31) ^ hash(self.topDomain)
    value = (value * 31) ^ hash(self.subDomain)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
